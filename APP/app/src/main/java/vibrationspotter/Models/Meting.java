package vibrationspotter.Models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/*-----
Versimpelde representatie van de Meting-klasse en gelijk aan de Doorstuurmeting-klasse op de server
hoofddoel: communicatie met behulp van Gson te vergemakkelijken
-----*/

public class Meting {
    private int idMeting;

    private String titel;

    private String tijdstip;

    private String opmerking;

    private String dataset1;

    private String dataset2;

    public Meting(int idMeting, String titel, String tijdstip, String opmerking, String dataset1, String dataset2) {
        this.idMeting = idMeting;
        this.titel = titel;
        this.tijdstip = tijdstip;
        this.opmerking = opmerking;
        this.dataset1 = dataset1;
        this.dataset2 = dataset2;
    }

    public Meting(String s, String s1, String meetdata) {
        this.titel = s;
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

    public String getDataset1() {
        return dataset1;
    }

    public String getDataset2() {
        return dataset2;
    }

    @Override
    public String toString() {
        String string = String.valueOf(idMeting);
        string = string.concat(",");
        string = string.concat(titel);
        string = string.concat(",");
        string = string.concat(tijdstip);
        string = string.concat(",");
        string = string.concat(opmerking);

        System.out.println(dataset1);

        return string;
    }

    public JSONArray toJArray() {
        JSONArray jArray = new JSONArray();
        JSONObject jObject = new JSONObject();
        try {
            jObject.put("idMeting", this.idMeting)
                    .put("titel", this.titel)
                    .put("tijdstip", this.tijdstip)
                    .put("opmerking", this.opmerking)
                    .put("dataset1", this.dataset1)
                    .put("dataset2", this.dataset2);
            jArray.put(jObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jArray;
    }
}
