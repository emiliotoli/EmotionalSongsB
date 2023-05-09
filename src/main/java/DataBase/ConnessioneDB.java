package DataBase;

import java.sql.*;

/**
 * in questa clase sono definiti i metoi che vengono usati per creare la connessione tra il server il DB EmotionaSongs_lab_B che si trova su posgre
 */
public class ConnessioneDB {

     static String port= "jdbc:postgresql://localhost:5432/EmotionaSongs_lab_B" ;
     static Connection connection;
     static String username = "postgres" ;
     static String password = "Kira0109@!";


     public static ConnessioneDB istance;

     public void setIstance(){
         if(istance==null){
             istance=this;
         }
     }


     /**Connessione al DataBase**/
     public Connection DBConnecctoin(){
         setIstance();
         try(Connection conn=DriverManager.getConnection(port,username,password)){
             istance.connection=conn;
             //connection=conn;
             System.out.println("connessione al DB riuscita");
             System.out.println("Connesso al DB");

         }catch (SQLException sqle){
             System.out.println("connessione al DB fallita");
             sqle.printStackTrace();

         }
         return connection;
     }

}
