package DoorstuurModels;

import java.util.Date;

import model.Project;

/**
 * Simple version of Project class. Used to simplify working with Gson
 * 
 * @author Birgen Vermang, Thomas Bruneel, Pieter-Jan Vanhaverbeke, Pieter
 *         Vanderhaegen
 *
 */
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

	public DoorstuurProject() {
	}

	/**
	 * Constructor
	 * 
	 * @param p
	 *            Project
	 */
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

	/**
	 * 
	 * Constructor
	 * 
	 * @param idProject
	 *            id project
	 * @param titel
	 *            Project title
	 * @param type
	 *            Type of project
	 * @param latitude
	 *            latitude coordinates of project location
	 * @param longtitude
	 *            longtitude coordinates of project location
	 * @param goedgekeurd
	 *            boolean to set if project is accepted
	 * @param qR
	 *            string to generate QR code
	 * @param timestamp
	 *            timestamp for validity check of QR
	 * @param beschrijving
	 *            extra comments project
	 */
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
