package DataBase;

import java.sql.*;
import java.security.*;

public class Query {

    private String ricercaTitolo;
    public void  ricercaTitolo(String Titolo ){
        ricercaTitolo="SELECT * FROM ..... WHERE titolo=?";
    }

    public void ricercaAutoreAnno(String autore, int anno){}


}
