package vibrationspotter.Models;

import android.util.Base64;

public class Meting {
    private int idMeting;

    private String titel;

    private String tijdstip;

    private String foto;

    private String opmerking;

    private byte[] dataset1;

    private byte[] dataset2;

    public Meting(int idMeting, String titel, String tijdstip, byte[] foto, String opmerking, byte[] dataset1, byte[] dataset2) {
        this.idMeting = idMeting;
        this.titel = titel;
        this.tijdstip = tijdstip;
        this.opmerking = opmerking;
        this.dataset1 = dataset1;
        this.dataset2 = dataset2;
        this.foto = Base64.encodeToString(foto, Base64.DEFAULT);


    }

    public Meting(String s, String s1, String imageString, byte[] meetdata) {
        this.idMeting = 666;
        this.titel = s;

    }

    public int getIdMeting() {
        return idMeting;
    }

    public String getTitel() {
        return titel;
    }

    public String getTijdstip() {
        return tijdstip;
    }

    public String getOpmerking() {
        return opmerking;
    }

    public byte[] getDataset1() {
        return dataset1;
    }

    public byte[] getDataset2() {
        return dataset2;
    }
}
