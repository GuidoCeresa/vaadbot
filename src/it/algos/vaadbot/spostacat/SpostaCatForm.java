package it.algos.vaadbot.spostacat;

import com.vaadin.ui.*;
import it.algos.vaad.wiki.Api;
import it.algos.vaad.wiki.TipoRisultato;
import it.algos.vaad.wiki.request.QueryCat;
import it.algos.vaad.wiki.request.QueryMove;
import it.algos.vaad.wiki.request.QueryWriteTitle;
import it.algos.vaadbot.sposta.SpostaForm;
import it.algos.webbase.web.dialog.ConfirmDialog;
import it.algos.webbase.web.field.CheckBoxField;
import it.algos.webbase.web.field.PasswordField;
import it.algos.webbase.web.field.TextField;
import it.algos.webbase.web.lib.LibText;

import java.util.ArrayList;

/**
 * Created by gac on 06 nov 2015.
 * .
 */
public class SpostaCatForm extends SpostaForm {

    public static final String WIKI_BASE = "https://it.wikipedia.org/";
    public static final String WIKI_URL = WIKI_BASE + "wiki/";
    private static final String CAT = "Categoria:";

    private HorizontalLayout layout;
    private VerticalLayout sinistra;
    private VerticalLayout destra;

    private TextField oldVoce;
    private TextField newVoce;
    private PasswordField passField;
    private CheckBoxField rememberField;
    private Table table;
    private Label bottomLabel;
    private CheckBoxField namespace;
    private CheckBoxField pagina;
    private CheckBoxField links;


    public SpostaCatForm() {
        super(null);
        init();
    }// end of constructor


    /**
     * Initialization <br>
     */
    private void init() {
        super.setModal(false);
        super.getConfirmButton().setCaption("Esegui");
        super.getConfirmButton().setClickShortcut(0, 0);

        layout = new HorizontalLayout();
        layout.setSpacing(true);
        layout.setHeight(ALT_FORM);
        layout.setWidth(LAR_FORM);
        layout.addComponent(creaSinistra());
        layout.addComponent(creaDestra());

        addComponent(layout);
        addComponent(creaBox());
    }// end of method

    /**
     * Initialization <br>
     */
    private VerticalLayout creaSinistra() {
        VerticalLayout sinistra = new VerticalLayout();
        sinistra.setMargin(true);
        sinistra.setSpacing(true);

        // creates the table
        table = new Table("Pagine nella categoria:");
        this.table.setWidth("100%");
        this.table.setHeight("320px");

        table.addContainerProperty("", String.class, null);
        table.setColumnHeaderMode(Table.ColumnHeaderMode.HIDDEN);

        table.setImmediate(true);

        // crea i campi
        oldVoce = new TextField("Vecchia categoria");
        oldVoce.setWidth(LAR_FIELD);
        oldVoce.addListener(new Component.Listener() {
            @Override
            public void componentEvent(Event event) {
                aggiornaTavola(event);
            }// end of inner method
        });// end of anonymous inner class

        bottomLabel = new Label();
        sinistra.addComponent(oldVoce);
        sinistra.addComponent(table);
        sinistra.addComponent(bottomLabel);
        sinistra.setExpandRatio(this.table, 1f);

        return sinistra;
    }// end of method

    /**
     * Initialization <br>
     */
    private VerticalLayout creaDestra() {
        VerticalLayout destra = new VerticalLayout();
        destra.setMargin(true);
        destra.setSpacing(true);

        PasswordField passField;

        // crea i campi
        newVoce = new TextField("Nuova categoria");
        newVoce.setWidth(LAR_FIELD);

        destra.addComponent(newVoce);

        return destra;
    }// end of method


    /**
     * Initialization <br>
     */
    private VerticalLayout creaBox() {
        VerticalLayout layoutBox = new VerticalLayout();
        layoutBox.setSpacing(true);

        pagina = new CheckBoxField("Sposta la categoria (rinomina)", true);
        layoutBox.addComponent(pagina);

        links = new CheckBoxField("Modifica i links", true);
        layoutBox.addComponent(links);

        return layoutBox;
    }// end of method

    private void aggiornaTavola(Event event) {
        String titoloVoce = getOldTitle();
        ArrayList<String> listaTitles;
        String txtLabel = "La categoria selezionata non esiste";

        table.removeAllItems();

        if (event == null) {
            return;
        }// end of if cycle

        listaTitles = listaPagine();

        if (listaTitles == null) {
            if (Api.esiste(titoloVoce)) {
                txtLabel = "La categoria è vuota";
            }// end of if cycle
        }// end of if cycle

        if (listaTitles != null && listaTitles.size() > 0) {
            for (String title : listaTitles) {
                table.addItem(new Object[]{title}, null);
            }// end of for cycle
            txtLabel = "Ci sono " + listaTitles.size() + " pagine nella categoria";
        }// end of if cycle

        bottomLabel.setValue(txtLabel);
    }// end of method

    private ArrayList<String> listaPagine() {
        String title = getOldTitle();
        ArrayList<String> listaTitles = null;

        if (oldVoce != null) {
            title = oldVoce.getValue();
        }// end of if cycle

        if (!title.equals("")) {
            listaTitles=Api.leggeTitlesCategoria(title);
        }// end of if cycle

        return listaTitles;
    }// end of method


    protected void onConfirm() {
        String msg = "Messaggio di controllo";
        String newMsg = "";
        boolean esisteDestinazione;

        if (oldVoce != null && oldVoce.getValue().equals("")) {
            Notification.show("Avviso", "Devi prima selezionare una voce da spostare", Notification.Type.WARNING_MESSAGE);
            return;
        }// end of if cycle

        if (newVoce != null && newVoce.getValue().equals("")) {
            Notification.show("Avviso", "Devi selezionare un nuovo titolo", Notification.Type.WARNING_MESSAGE);
            return;
        }// end of if cycle

        if (isPagina()) {
            esisteDestinazione = Api.esiste(newVoce.getValue());
            if (esisteDestinazione) {
                Notification.show("Avviso", "Il titolo selezionato esiste già", Notification.Type.WARNING_MESSAGE);
                return;
            }// end of if cycle
        }// end of if cycle

        newMsg += "Sei sicuro di voler spostare la voce ";
        newMsg += oldVoce.getValue();
        newMsg += " al nuovo titolo ";
        newMsg += newVoce.getValue();
        newMsg += " ?";
        newMsg += "<br>Tutti i riferimenti delle voci indicate verranno modificati col nuovo wikilink";
        ConfirmDialog dialog = new ConfirmDialog(msg, newMsg, new ConfirmDialog.Listener() {
            @Override
            public void onClose(ConfirmDialog dialog, boolean confirmed) {
                if (confirmed) {
                    esegue();
                }// end of if cycle
            }// end of inner method
        });// end of anonymous inner class
        if (getUI() != null) {
            dialog.show(getUI());
        }// end of if/else cycle

//        close();
    }// end of method

    private String getOldTitle() {
        String title = "";

        if (oldVoce != null) {
            title = oldVoce.getValue();
        }// end of if cycle

        return title;
    }// end of method

    private String getNewTitle() {
        String title = "";

        if (newVoce != null) {
            title = newVoce.getValue();
        }// end of if cycle

        return title;
    }// end of method

    private boolean isNamespace() {
        boolean status = false;

        if (namespace != null) {
            status = namespace.getValue();
        }// end of if cycle

        return status;
    }// end of method

    private boolean isPagina() {
        boolean status = false;

        if (pagina != null) {
            status = pagina.getValue();
        }// end of if cycle

        return status;
    }// end of method

    private boolean isLinks() {
        boolean status = false;

        if (links != null) {
            status = links.getValue();
        }// end of if cycle

        return status;
    }// end of method

    private void esegue() {
        boolean spostata = true;

        if (isPagina()) {
            spostata = spostaPagina();
        }// end of if cycle

        if (isLinks() && spostata) {
            modificaLink();
        }// end of if cycle
    }// end of method

    private boolean spostaPagina() {
        boolean spostata = false;
        String from = CAT + this.getOldTitle();
        String to = CAT + this.getNewTitle();

        QueryMove query = new QueryMove(from, to);
        if (query.getRisultato() == TipoRisultato.spostata) {
            spostata = true;
        }// end of if cycle

        return spostata;
    }// end of method

    private void modificaLink() {
        ArrayList<String> listaTitles = listaPagine();
        String oldTitle = getOldTitle();
        String newTitle = getNewTitle();
        String oldLink = "[[" + CAT + oldTitle;
        String newLink = "[[" + CAT + newTitle;
        String oldTesto;
        String newTesto;
        String summary = "GacBot fix cat";
        String tagPar = ")";
        String tagPipe = "|";

        if (newLink.endsWith(tagPar)) {
            newLink += tagPipe;
        }// end of if cycle

        if (listaTitles != null && listaTitles.size() > 0) {
            for (String title : listaTitles) {
                oldTesto = Api.leggeVoce(title);
                newTesto = LibText.sostituisce(oldTesto, oldLink, newLink);
                new QueryWriteTitle(title, newTesto, summary);
            }// end of for cycle
        }// end of if cycle

    }// end of method

}// end of class
