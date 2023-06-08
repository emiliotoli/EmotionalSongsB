package ClientES;

public class Client_Impl extends Thread{
        public Client_Impl(int i) {
        super(String.valueOf(i));
        start();
    }

    public void run(){

    }

}
