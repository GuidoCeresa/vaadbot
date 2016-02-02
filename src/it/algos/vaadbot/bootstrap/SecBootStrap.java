package it.algos.vaadbot.bootstrap;

import it.algos.webbase.web.bootstrap.SecurityBootStrap;
import it.algos.webbase.web.lib.LibSecurity;

import javax.servlet.ServletContextEvent;

/**
  * Security dell'applicazione
  * Executed on container startup
  * Setup non-UI logic here
  * <p>
  * Classe eseguita solo quando l'applicazione viene caricata/parte nel server (Tomcat od altri) <br>
  * Eseguita quindi ad ogni avvio/riavvio del server e NON ad ogni sessione <br>
  * Se usata, è OBBLIGATORIO aggiungere questa classe nei listeners del file web.WEB-INF.web.xml
  * Se non utilizzata, può essere cancellata:
  * 1) - deve essere cancellata come classe
  * 2) - deve essere cancellata come listener del file web.WEB-INF.web.xml
  */
public class SecBootStrap extends SecurityBootStrap {

    private String botRole = "bot";
    private String botName = "gacbot";


    /**
     * Executed on container startup
     * Setup non-UI logic here
     * <p/>
     * This method is called prior to the servlet context being
     * initialized (when the Web application is deployed).
     * You can initialize servlet context related data here.
     * <p/>
     * Viene sovrascritta dalla sottoclasse:
     * - per controllare e creare eventuali ruoli specifici <br>
     * - per controllare gli utenti abilitati esistenti e creare quelli eventualmente mancanti <br>
     * Deve (DEVE) richiamare anche il metodo della superclasse (questo)
     * prima (PRIMA) di eseguire le regolazioni specifiche <br>
     */
    @Override
    public void contextInitialized(ServletContextEvent contextEvent) {
        super.contextInitialized(contextEvent);

        this.creaRuoli();
        this.creaUtenti();
    }// end of method


    /**
     * This method is invoked when the Servlet Context
     * (the Web application) is undeployed or
     * WebLogic Server shuts down.
     */
    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }// end of method

    /**
     * Crea una serie di ruoli
     * <p/>
     * Alcuni generali controlla se esistono (dovrebbero esserci) e li crea solo se mancano (la prima volta)
     * Alcuni specifici di questa applicazione e li crea
     */
    private void creaRuoli() {
        this.creaRuolo(botRole);
    }// end of method

    /**
     * Crea una serie di utenti
     */
    private void creaUtenti() {
        LibSecurity.creaUtente(botName, "", botRole);
    }// end of method

}// end of bootstrap class
