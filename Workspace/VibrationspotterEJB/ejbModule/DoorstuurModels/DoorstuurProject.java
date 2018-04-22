package DoorstuurModels;

import java.util.Date;

import model.Project;

/*-----
Versimpelde versie van de in de databank aanwezige Project-klasse
Hoofddoel: gebruik van Gson te vergemakkelijken
-----*/

public class DoorstuurProject {
	
	private int idProject;

	private String titel;

	private String type;
	
	private float latitude;

	private float longtitude;

	private boolean goedgekeurd;

	private String QR;

	private Date timestamp;

	private String beschrijving;

	public DoorstuurProject(){}

	public DoorstuurProject(Project p) {
		this.idProject = p.getIdProject();
		this.titel = p.getTitel();
		this.type = p.getType();
		this.latitude = p.getLatitude();
		this.longtitude = p.getLongtitude();
		this.goedgekeurd = p.isGoedgekeurd();
		this.QR = p.getQR();
		this.timestamp = p.getTimestamp();
		this.beschrijving = p.getBeschrijving();
	}

	public DoorstuurProject(int idProject, String titel, String type, float latitude, float longtitude,
			boolean goedgekeurd, String qR, Date timestamp, String beschrijving) {
		super();
		this.idProject = idProject;
		this.titel = titel;
		this.type = type;
		this.latitude = latitude;
		this.longtitude = longtitude;
		this.goedgekeurd = goedgekeurd;
		QR = qR;
		this.timestamp = timestamp;
		this.beschrijving = beschrijving;
	}
	
	
	
	

}
