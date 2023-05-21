package ClientES;

import serverES.ServerInterfaceNonLoggato;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public interface MetodiControlli_Client {

    static int cf_Lunghezza = 16; // usato per verificare la lunghezza del codice fiscale
    Scanner scan =new Scanner(System.in);

    private static String soloLettere(String nominativo) throws IOException {
        do {
            if (nominativo.matches("[a-zA-Z]*${3,20}")) {
                break;
            } else {
                System.out.println("il nominativo appena inserito contiene numeri o caratteri speciali. Deve contenere lettere");
                System.out.println("digitare nuovamente il nominativo");
                nominativo = scan.nextLine();
            }
        } while (!(nominativo.matches("[a-zA-Z]*${3,20}")));
        return nominativo;
    }
    static String lunghezzaNominativo(String nominativo) throws IOException {
        do {
            if (nominativo != null) {
                if(nominativo.length() >= 3){
                    break;
                }
            } else {
                System.out.println("non hai inserito nessun nome o il nome inserito è tropppo corto.");
                System.out.print("reinsirire il nome: ");
                nominativo = scan.nextLine();
            }
        } while (nominativo == null || nominativo.length() < 3);
        soloLettere(nominativo);
        return nominativo;
    }

    static String formatoCF(String codFisc) throws IOException {
        do {
            if (codFisc.length() == cf_Lunghezza){
                if(codFisc.matches("([A-Za-z]{6})([0-9]{2})([A-Za-z])([0-9]{2})([A-Za-z])([0-9]{3})([A-Za-z])")){
                    break;
                }
            } else {
                System.out.println("inserimento non valido");
                System.out.print("inserisci nuovamente il Codice Fiscale: ");
                codFisc = scan.nextLine();
            }
        } while ((codFisc.length() != cf_Lunghezza) && !codFisc.matches("([A-Za-z]{6})([0-9]{2})([A-Za-z])([0-9]{2})([A-Za-z])([0-9]{3})([A-Za-z])"));
        return codFisc;
    }

    public static String formatoNumeroCivico(String numcivico)throws IOException {
        while(!numcivico.matches("[0-9]+([A-Za-z]*)")) {
            System.out.println("il numero civico inserito non rispetta il formato");
            System.out.println("Deve iniziare con un numero di lunghezza massima di tre cifre ed una lettera.");
            System.out.println("reinserire il numero civico");
            numcivico = scan.nextLine();
        }
        return numcivico;
    }

    static String formatoCAP(int cap) throws IOException {

        boolean check = false;
        boolean checkLunghezzaCap=false;

        do{
            if (cap == 5) {
                checkLunghezzaCap=true;
                break;
            }
            else {
                System.out.println("Il CAP deve essere composto esattamente da 5 cifre.");
                System.out.println("reinserire il cap");
                cap=scan.nextInt();
            }
        }while (!checkLunghezzaCap);

        cap=isNumeric(cap);

        do {
            if (cap < 10 || cap > 97100) {
                System.out.println("cap non valido");
                System.out.println("reinserire il cap: ");
                cap = Integer.parseInt(scan.nextLine());

            } else {
                check = true;
            }
        } while (!check);

        StringBuilder CAP = new StringBuilder();
        while (CAP.length() < 5 - Integer.toString(cap).length()) {
            CAP.append(0);
        }
        CAP.append(cap);

        return CAP.toString();
    }

    static int isNumeric(int cap) {
        boolean chekNumeri = false;
        do{
            String capString = String.valueOf(cap);
            for (int i = 0; i < capString.length(); i++) {
                if (Character.isDigit(capString.charAt(i))) {
                    chekNumeri=true;
                    break;
                }
                else{
                    System.out.println("Il CAP deve contenere solo caratteri numerici");
                    System.out.println("reinserire il cap");
                    cap=scan.nextInt();
                }
            }
        }while(!chekNumeri);
        return cap;

    }

    static String isNotNULL(String str) throws IOException {
        do{
            if(str.length()==0){
                System.out.println("non hai inserito niente.");
                System.out.println("inserisci nuovamente il comune: ");
                str =scan.nextLine();
            }
            else{
                break;
            }
        }while(str.length()==0);
        str=formatoComProv(str);
        return str;
    }
    private static String formatoComProv(String str) throws IOException {
        do{
            if(str.matches("[A-Za-z]*")){
                break;
            }
            else{
                System.out.println("la stringa inserita non puo' contenerte dei caratteri numerici o  caratteri speciali");
                System.out.println("reinserire la stringa: ");
                str=scan.nextLine();
            }

        }while(!(str.matches("[A-Za-z]*")));
        return str;
    }

    static String formatoMail(String mail) throws IOException {
        do {
            if (mail.matches("[a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,3}")) {
                break;
            } else {
                System.out.println("inserimento della mail non valido");
                System.out.print("reinserisci la mail: ");
                mail = scan.nextLine();
            }
        } while (!mail.matches("[a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,3}"));
        return mail;
    }

    /*static String userIDExists(String username)throws IOException {
        boolean esisteut;
        do {
            esisteut = esisteUtente(username);

            if (!esisteut) {
                System.out.println("Nome_Utente valido.");
                break;
            } else {
                System.out.println("Nome_Utente gia' esistente!\nScegliere un nuovo nome utente. ");
                System.out.println("Inserire il nuovo nome utente: ");
                username = scan.nextLine();
            }
        } while (esisteut);

        username = formatoUser(username);
        return username;
    }*/
    //static boolean esisteUtente(String nomeUtente) throws IOException {
        // il metodo deve mandare il nome utente scelto al server che crea una connessione con il db e verifica se esiste quell'utente
        //se esiste il server risponderà con TRUE--> bisogna reinserire l'utente;
        // se non esiste il server risponderà con FALSE--> nome utente inserito valido
        //Registry regis= LocateRegistry.getRegistry(1099);
        //ServerInterfaceNonLoggato serInterfaccia;

    //}
    private static String formatoUser(String user) throws IOException {
        do {
            if (user.matches("^[a-zA-Z0-9_-]{3,15}$")) {
                break;
            } else {
                System.out.println("inserimento dello userId non valido. Non è conforme al formato richiesto");
                System.out.print("reinserisci lo UserId: ");
                user = scan.nextLine();
            }
        } while (!user.matches("^[a-zA-Z0-9_-]{3,15}$"));
        return user;
    }


}
