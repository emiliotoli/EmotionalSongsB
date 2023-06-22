package ClientES;

import javax.print.DocFlavor;
import java.io.Serializable;

public class PlayList implements Serializable {
    private static final long serialVersionUID = 1L;

    private  String nomePlaylist;
    private  String userId;

    public PlayList(String user, String name){
        this.userId=user;
        this.nomePlaylist=name;

    }

    public  String getnomePlalist(){return  nomePlaylist;}
    public  String getInfo(){return userId+nomePlaylist;}





}
