package ClientES;

import java.io.*;

/**@author Emilio Daverio - Stefano Farina
 * Questa interfaccia fornisce metodi di validazione e controllo per diversi campi dati.
 */
public interface MetodiControlli_Client {

    static int cf_Lunghezza = 16; // usato per verificare la lunghezza del codice fiscale
    InputStreamReader isr = new InputStreamReader(System.in);
    BufferedReader read = new BufferedReader(isr);


    /**@author Emilio Daverio - Stefano Farina
     * Metodo che verifica se una stringa contiene solo lettere.
     *
     * @param nominativo La stringa da controllare.
     *
     * @return true se la stringa contiene solo lettere, altrimenti false.
     *
     * @throws IOException Se si verifica un errore durante l'input/output.
     */
    private static boolean soloLettere(String nominativo) throws IOException {

        if (nominativo.matches("[a-zA-Z]*${3,20}")) {
            return true;
        } else {

            return false;
        }
    }


    /**@author Emilio Daverio - Stefano Farina
     * Metodo che verifica la lunghezza minima del nominativo e il formato .
     *
     * @param nominativo Il nominativo da controllare.
     * @return true se il nominativo ha una lunghezza valida e contiene solo lettere, altrimenti false.
     * @throws IOException Se si verifica un errore durante l'input/output.
     */
    static boolean lunghezzaNominativo(String nominativo) throws IOException {
        boolean controllo;
        if (nominativo != null) {
            if (nominativo.length() >= 3) {
                controllo = soloLettere(nominativo);
                if (controllo) {
                    return true;
                }
            }
        } else {
            return false;
        }
        return false;
    }


    /**@author Emilio Daverio - Stefano Farina
     * Metodo che verifica il formato del codice fiscale italiano.
     *
     * @param codFisc Il codice fiscale da controllare.
     *
     * @return true se il codice fiscale è nel formato corretto, altrimenti false.
     *
     * @throws IOException Se si verifica un errore durante l'input/output.
     */
    static boolean formatoCF(String codFisc) throws IOException {

        if (codFisc.length() == cf_Lunghezza) {
            if (codFisc.matches("([A-Za-z]{6})([0-9]{2})([A-Za-z])([0-9]{2})([A-Za-z])([0-9]{3})([A-Za-z])")) {
                return true;
            }
        } else {

            return false;
        }
        return false;
    }


    /**@author Emilio Daverio - Stefano Farina
     * Metodo che verifica il formato di un numero civico.
     *
     * @param numcivico Il numero civico da controllare.
     *
     * @return true se il numero civico ha il formato corretto, altrimenti false.
     *
     * @throws IOException Se si verifica un errore durante l'input/output.
     */
    static boolean formatoNumeroCivico(String numcivico) throws IOException {
        if (numcivico.matches("[0-9]+([A-Za-z]*)")) {

            return true;
        } else {
            return false;
        }
    }


    /**@author Emilio Daverio - Stefano Farina
     * Metodo che verifica il formato di un CAP italiano.
     *
     * @param cap Il CAP da controllare.
     *
     * @return true se il CAP è nel formato corretto, altrimenti false.
     *
     * @throws IOException Se si verifica un errore durante l'input/output.
     */
    static boolean formatoCAP(int cap) throws IOException {
        String capStr = Integer.toString(cap);

        if (capStr.length() != 5) {
            return false;
        }

        if (cap < 10 || cap > 97100) {
            return false;

        }
        return true;

    }


    /**@author Emilio Daverio - Stefano Farina
     * Metodo che verifica che una stringa non sia nulla (null) e abbia una lunghezza maggiore di zero.
     *
     * @param str La stringa da controllare.
     *
     * @return true se la stringa non è nulla e ha lunghezza maggiore di zero, altrimenti false.
     *
     * @throws IOException Se si verifica un errore durante l'input/output.
     */
    static boolean isNotNULL(String str) throws IOException {
        boolean controllo;
        if (str.length() == 0) {
            return false;
        } else {
            controllo = formatoComProv(str);
            if (controllo) {
                return true;
            } else {
                return false;
            }
        }
    }


    /**@author Emilio Daverio - Stefano Farina
     * Metodo che verifica il formato del comune della provincia (contiene solo lettere).
     *
     * @param str Il comune o provincia da controllare.
     *
     * @return true se la stringa contiene solo lettere, altrimenti false.
     *
     * @throws IOException Se si verifica un errore durante l'input/output.
     */
    private static boolean formatoComProv(String str) throws IOException {

        if (str.matches("[A-Za-z]*")) {
            return true;
        } else {

            return false;
        }
    }


    /**@author Emilio Daverio - Stefano Farina
     * Metodo che verifica il formato di un indirizzo email.
     *
     * @param mail L'indirizzo email da controllare.
     *
     * @return true se l'indirizzo email è nel formato corretto, altrimenti false.
     *
     * @throws IOException Se si verifica un errore durante l'input/output.
     */
    static boolean formatoMail(String mail) throws IOException {

        if (mail.matches("[a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,3}")) {
            return true;
        } else {
            return false;
        }
    }


    /**@author Emilio Daverio - Stefano Farina
     * Metodo che verifica il formato di un nome utente (username).
     *
     * @param user Il nome utente da controllare.
     *
     * @return true se il nome utente è nel formato corretto, altrimenti false.
     *
     * @throws IOException Se si verifica un errore durante l'input/output.
     */
    static boolean formatoUser(String user) throws IOException {

        if (user.matches("^[a-zA-Z0-9_-]{3,15}$")) {
            return true;
        } else {

            return false;
        }
    }


    /**@author Emilio Daverio - Stefano Farina
     * Metodo che verifica il formato di una password inserita.
     *
     * @param pwdScelta La password da controllare.
     * @return true se la password è nel formato corretto, altrimenti false.
     * @throws IOException Se si verifica un errore durante l'input/output.
     */
    static boolean FormatoPassword(String pwdScelta) throws IOException {
        String confermaPwd;
        boolean controllo;

        if (pwdScelta != null) {

            controllo = checkFormato(pwdScelta);
            if (controllo) {
                return true;
            } else {
                return false;
            }
        } else {

            return false;
        }
    }


    private static boolean checkFormato(String pass) throws IOException {

        if (pass.matches("((?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%!_.]).{8,20})")) {
            return true;
        } else {

            return false;
        }
    }


    /**@author Emilio Daverio - Stefano Farina
     * Metodo che verifica se due password sono uguali.
     *
     * @param pw1 La prima password.
     * @param pw2 La seconda password.
     *
     * @return true se le due password sono uguali, altrimenti false.
     *
     * @throws IOException Se si verifica un errore durante l'input/output.
     */
    static boolean checkPassordUguale(String pw1, String pw2) throws IOException {

        if (pw1.equals(pw2)) {

            return true;
        } else {

            return false;
        }

    }
}
