package it.algos.vaadbot.sposta;

import com.vaadin.data.Item;
import com.vaadin.server.Resource;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Window;
import it.algos.vaadbot.spostavoce.SpostaVoceForm;
import it.algos.webbase.web.menu.AMenuBar;
import it.algos.webbase.web.module.ModulePop;
import it.algos.webbase.web.navigator.NavPlaceholder;

import java.util.List;

/**
 * Created by gac on 15 nov 2015.
 * .
 */
public abstract class SpostaMod extends ModulePop {

    protected SpostaForm form;

    public SpostaMod(String menuAddress) {
        super(null, menuAddress);
        setCompositionRoot(tablePortal);
    }// end of basic constructor


    /**
     * Create the MenuBar Item for this module
     * <p>
     * Invocato dal metodo AlgosUI.creaMenu()
     * PUO essere sovrascritto dalla sottoclasse
     *
     * @param menuBar     a cui agganciare il menuitem
     * @param placeholder in cui visualizzare il modulo
     * @param icon        del menuitem
     * @return menuItem appena creato
     */
    protected MenuBar.MenuItem createMenuItem(final MenuBar menuBar, final NavPlaceholder placeholder, Resource icon) {
        MenuBar.MenuItem menuItem;

        menuItem = menuBar.addItem(getMenuAddress(), icon, new MenuBar.Command() {
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                placeholder.setContent(null);
                deselezionaAllItemButOne(menuBar, selectedItem);
                esegue(placeholder);
            }// end of inner method
        });// end of anonymous inner class

        menuItem.setStyleName(AMenuBar.MENU_DISABILITATO);

        super.menuItem = menuItem;

        return menuItem;
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

    protected void esegue(NavPlaceholder placeholder) {
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
