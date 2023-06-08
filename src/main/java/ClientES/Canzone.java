package ClientES;

import java.io.Serializable;

public class Canzone implements Serializable {
    private static final long serialVersionUid = 1L;

    private static String titoloCanzone;
    private static String autoreCanzone;
    private static int annoCanzone;

    public Canzone (String name, String autore, int anno){
        Canzone.titoloCanzone=name;
        Canzone.autoreCanzone=autore;
        Canzone.annoCanzone=anno;
    }

    public static String getTitoloCanzone(){return titoloCanzone;}
    public static String getAutoreCanzone(){return autoreCanzone;}
    public static int getAnnoCanzone(){return annoCanzone;}
    public  static String getInfoCanzone(){return titoloCanzone+autoreCanzone+annoCanzone;}
}
