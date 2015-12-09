package it.algos.vaadbot.spostacat;

import com.vaadin.data.Item;
import com.vaadin.server.Resource;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import it.algos.vaadbot.sposta.SpostaForm;
import it.algos.vaadbot.sposta.SpostaMod;
import it.algos.webbase.web.menu.AMenuBar;
import it.algos.webbase.web.module.ModulePop;
import it.algos.webbase.web.navigator.NavPlaceholder;

import java.util.List;

/**
 * Created by gac on 06 nov 2015.
 * .
 */
public class SpostaCatMod extends SpostaMod {

    // indirizzo interno del modulo (serve nei menu)
    public static String MENU_ADDRESS = "Sposta Cat";


    public SpostaCatMod() {
        super(MENU_ADDRESS);
    }// end of basic constructor


    protected void esegue(NavPlaceholder placeholder) {
        SpostaCatForm form = new SpostaCatForm();
        UI video = placeholder.getUI();
        video.addWindow(form);
    }// end of method


    protected SpostaForm createForm() {
        return new SpostaCatForm();
    }// end of metho


}// end of class
