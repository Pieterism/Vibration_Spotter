package vibrationspotter.Models;

import java.util.Date;

public class Project {

    private int idProject;

    private String titel;

    private String type;

    private float latitude;

    private float longtitude;

    private boolean goedgekeurd;

    private String QR;

    private Date timestamp;

    private String beschrijving;

    public Project() {
    }

    public Project(int idProject, String titel, String type, float latitude, float longtitude, boolean goedgekeurd, String QR, Date timestamp, String beschrijving) {
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
}
