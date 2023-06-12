package ClientES;

import java.io.Serializable;

public class Canzone implements Serializable {
    private static final long serialVersionUid = 1L;

    private  String titoloCanzone;
    private  String autoreCanzone;
    private  int annoCanzone;

    public Canzone (String name, String autore, int anno){
        titoloCanzone=name;
        autoreCanzone=autore;
        annoCanzone=anno;
    }

    public  String getTitoloCanzone(){return titoloCanzone;}
    public  String getAutoreCanzone(){return autoreCanzone;}
    public  int getAnnoCanzone(){return annoCanzone;}
}
