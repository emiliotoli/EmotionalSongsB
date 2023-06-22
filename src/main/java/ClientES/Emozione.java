package ClientES;

import java.io.Serializable;

public class Emozione implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nomeEmozione;
    private String tipoEmozione;
    private String spiegazioneEmozione;
    private int punteggioEmozione;
    private double percentualeEmozione;


    public Emozione(){
    }
    public Emozione(String nome, String tipo, String spiegazione, int valore, double percentuale){
        this.nomeEmozione=nome;
        this.tipoEmozione=tipo;
        this.spiegazioneEmozione=spiegazione;
        this.punteggioEmozione=valore;
        this.percentualeEmozione=percentuale;
    }
    public Emozione (String nome, double percentuale){
        this.nomeEmozione=nome;
        this.percentualeEmozione=percentuale;
    }

    public String getNomeEmozione(){return nomeEmozione;}

    public String getTipoEmozione(){return tipoEmozione;}
    public String getSpiegazioneEmozione(){return spiegazioneEmozione;}
    public int getPunteggioEmozione(){return punteggioEmozione;}
    public double getPercentualeEmozione(){return  percentualeEmozione;}

}
