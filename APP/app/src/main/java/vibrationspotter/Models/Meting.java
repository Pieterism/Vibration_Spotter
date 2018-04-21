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

    public Meting(int idMeting, String titel, String tijdstip, String foto, String opmerking, byte[] dataset1, byte[] dataset2) {
        this.idMeting = idMeting;
        this.titel = titel;
        this.tijdstip = tijdstip;
        this.foto = foto;
        this.opmerking = opmerking;
        this.dataset1 = dataset1;
        this.dataset2 = dataset2;
    }

    public Meting(String s, String s1, String imageString, byte[] meetdata) {
        this.idMeting = 666;
        this.titel = s;
        this.tijdstip = "placeholder";
        this.foto = imageString;
        this.opmerking = s1;
        this.dataset1 = meetdata;
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
