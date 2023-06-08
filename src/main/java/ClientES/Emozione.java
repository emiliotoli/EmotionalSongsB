package ClientES;

import java.io.Serializable;

public class Emozione implements Serializable {
    private static final long serialVersionUid = 1L;
    private static String nomeEmozione;

    public Emozione(){
    }

    public static String getNomeEmozione(){return nomeEmozione;}

}
