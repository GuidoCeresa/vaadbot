package it.algos.vaadbot.spostavoce;

import com.vaadin.ui.UI;
import it.algos.vaadbot.sposta.SpostaForm;
import it.algos.vaadbot.sposta.SpostaMod;
import it.algos.webbase.web.navigator.NavPlaceholder;

/**
 * Created by gac on 06 nov 2015.
 * .
 */
public class SpostaVoceMod extends SpostaMod {

    // indirizzo interno del modulo (serve nei menu)
    public static String MENU_ADDRESS = "Sposta Voce";


    public SpostaVoceMod() {
        super(MENU_ADDRESS);
    }// end of basic constructor


    protected void esegue(NavPlaceholder placeholder) {
        SpostaVoceForm form = new SpostaVoceForm();
        UI video = placeholder.getUI();
        video.addWindow(form);
    }// end of method

    protected SpostaForm createForm() {
        return new SpostaVoceForm();
    }// end of metho


}// end of class
