package it.algos.vaadbot.sposta;

import it.algos.webbase.web.dialog.ConfirmDialog;

/**
 * Created by gac on 15 nov 2015.
 * .
 */
public abstract class SpostaForm extends ConfirmDialog {
    public SpostaForm(Listener closeListener) {
        super(closeListener);
    }
}
