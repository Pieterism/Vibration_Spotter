package vibrationspotter.Models;

/*-----
Versimpelde representatie van de Project-klasse en gelijk aan de DoorstuurProject-klasse op de server
hoofddoel: communicatie met behulp van Gson te vergemakkelijken
-----*/


public class Project {

    private int idProject;

    private String titel;

    private String type;

    private float latitude;

    private float longtitude;

    private boolean goedgekeurd;

    private String QR;

    private String timestamp;

    private String beschrijving;

    public Project() {
    }

    public Project(int idProject, String titel, String type, float latitude, float longtitude, boolean goedgekeurd, String QR, String timestamp, String beschrijving) {
        this.idProject = idProject;
        this.titel = titel;
        this.type = type;
        this.latitude = latitude;
        this.longtitude = longtitude;
        this.goedgekeurd = goedgekeurd;
        this.QR = QR;
        this.timestamp = timestamp;
        this.beschrijving = beschrijving;
    }

    public int getIdProject() {
        return idProject;
    }

    public String getTitel() {
        return titel;
    }

    public String getType() {
        return type;
    }

    public float getLatitude() {
        return latitude;
    }

    public float getLongtitude() {
        return longtitude;
    }

    public boolean isGoedgekeurd() {
        return goedgekeurd;
    }

    public String getQR() {
        return QR;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getBeschrijving() {
        return beschrijving;
    }
}
