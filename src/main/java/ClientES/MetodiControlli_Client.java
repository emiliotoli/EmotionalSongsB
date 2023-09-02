package ClientES;

import java.io.*;

public interface MetodiControlli_Client {

    static int cf_Lunghezza = 16; // usato per verificare la lunghezza del codice fiscale
    InputStreamReader isr = new InputStreamReader(System.in);
    BufferedReader read = new BufferedReader(isr);

    private static boolean soloLettere(String nominativo) throws IOException {

        if (nominativo.matches("[a-zA-Z]*${3,20}")) {
            return true;
        } else {

            return false;
        }
    }

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

    static boolean formatoNumeroCivico(String numcivico) throws IOException {
        if (numcivico.matches("[0-9]+([A-Za-z]*)")) {

            return true;
        } else {
            return false;
        }
    }

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

    private static boolean formatoComProv(String str) throws IOException {

        if (str.matches("[A-Za-z]*")) {
            return true;
        } else {

            return false;
        }
    }

    static boolean formatoMail(String mail) throws IOException {

        if (mail.matches("[a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,3}")) {
            return true;
        } else {
            return false;
        }
    }

    static boolean formatoUser(String user) throws IOException {

        if (user.matches("^[a-zA-Z0-9_-]{3,15}$")) {
            return true;
        } else {

            return false;
        }
    }

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

    static boolean checkPassordUguale(String pw1, String pw2) throws IOException {

        if (pw1.equals(pw2)) {

            return true;
        } else {

            return false;
        }

    }
}
