package ClientES;

import javax.print.DocFlavor;
import java.io.Serializable;

public class PlayList implements Serializable {
    private static final long serialVersionUid = 1L;

    private static String nomePlaylist;
    private static String userId;

    public PlayList(String user, String name){
        PlayList.nomePlaylist=name;
        PlayList.userId=user;
    }

    public static String getnomePlalist(){return nomePlaylist;}
    public static String getInfo(){return userId+nomePlaylist;}





}
