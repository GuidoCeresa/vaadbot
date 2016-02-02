package it.algos.vaadbot.sposta;

import it.algos.webbase.web.dialog.ConfirmDialog;

/**
 * Created by gac on 15 nov 2015.
 * .
 */
public abstract class SpostaForm extends ConfirmDialog {

    protected String ALT_FORM = "500px";
    protected String LAR_FORM = "1000px";
    protected String LAR_FIELD = "500px";

    public SpostaForm(Listener closeListener) {
        super(closeListener);
    }// end of constructor

}// end of class
