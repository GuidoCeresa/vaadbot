package it.algos.vaadbot.sposta;

import com.vaadin.data.Item;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import it.algos.vaadbot.spostavoce.SpostaVoceForm;
import it.algos.webbase.web.menu.AMenuBar;
import it.algos.webbase.web.module.ModulePop;

import java.util.List;

/**
 * Created by gac on 15 nov 2015.
 * .
 */
public abstract class SpostaMod extends ModulePop {

    protected SpostaForm form;

    /**
     * Costruttore senza parametri
     * <p/>
     * Invoca la superclasse passando i parametri:
     * (obbligatorio) la Entity specifica
     * (facoltativo) etichetta del menu (se manca usa il nome della Entity)
     * (facoltativo) icona del menu (se manca usa un'icona standard)
     */
    public SpostaMod(String menuAddress) {
        super(null, menuAddress);
        setCompositionRoot(tablePortal);
    }// end of constructor

    /**
     * Crea i sottomenu specifici del modulo
     * <p/>
     * Invocato dal metodo AlgosUI.addModulo()
     * Sovrascritto dalla sottoclasse
     *
     * @param menuItem principale del modulo
     */
    @Override
    public void addSottoMenu(MenuBar.MenuItem menuItem) {
        addCommandDialog(menuItem);
    }// end of method


    /**
     * Create the MenuBar Item for this module
     * <p/>
     * Invocato dal metodo AlgosUI.creaMenu()
     * PUO essere sovrascritto dalla sottoclasse
     */
    private void addCommandDialog(MenuBar.MenuItem menuItem) {
        menuItem.addItem("Dialogo", FontAwesome.COG, new MenuBar.Command() {
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                esegue();
            }// end of inner method
        });// end of anonymous inner class
    }// end of method


    /**
     * Regola l'aspetto di tutti i menu <br>
     */
    protected void deselezionaAllItemButOne(MenuBar menuBar, MenuBar.MenuItem selectedItem) {
        List<MenuBar.MenuItem> lista = menuBar.getItems();

        for (MenuBar.MenuItem menuItem : lista) {
            if (menuItem != selectedItem) {
                menuItem.setStyleName(AMenuBar.MENU_DISABILITATO);
            }// end of if cycle
        }// fine del ciclo for

        if (selectedItem != null) {
            selectedItem.setStyleName(AMenuBar.MENU_ABILITATO);
        }// end of if cycle
    }// end of method

    protected void esegue() {
        SpostaForm form = createForm();
        UI video = this.getUI();

        if (video != null&&form!=null) {
            video.addWindow(form);
        }// end of if cycle

    }// end of method


    /**
     * Edits an Item in a Form
     *
     * @param item      the item
     * @param newRecord if the edited item is a new record
     * @param caption   title for the window
     */
    public void editItem(Item item, boolean newRecord, String caption) {

        final SpostaForm form = createForm();

        if (form != null) {

            final Window window = new Window(caption, form);
            window.setResizable(false);
            if (this.isModale()) {
                window.setModal(true);
            }// end of if cycle

            form.setHeightUndefined();
            window.center();
            this.getUI().addWindow(window);

        }// end of if cycle

    }// end of method

    protected SpostaForm createForm() {
        return null;
    }// end of method

}// end of class
