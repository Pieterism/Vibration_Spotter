package model;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * This class implements a database Project table and the getters and setters
 * for the database values.
 * 
 * @author Birgen Vermang, Thomas Bruneel, Pieter-Jan Vanhaverbeke, Pieter
 *         Vanderhaegen
 *
 */
@Entity
@Table(name = "Project")
@NamedQuery(name = "Project.findAll", query = "SELECT p FROM Project p")
public class Project implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idProject", nullable = false)
	private int idProject;

	@Column(name = "titel", nullable = false)
	private String titel;

	@Column(name = "type", nullable = false)
	private String type; // aan elk type meting een int toekennen en adhv deze
							// int
							// bepalen welk type het is.

	@Column(name = "latitude", nullable = false)
	private float latitude;

	@Column(name = "longtitude", nullable = false)
	private float longtitude;

	@Column(name = "goedgekeurd", nullable = false)
	private boolean goedgekeurd;

	@Column(name = "QR", nullable = false)
	private String QR;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "timestamp")
	private Date timestamp;

	@Lob
	@Column(name = "beschrijving", length = 512)
	private String beschrijving;

	@ManyToOne
	@JoinColumn(name = "idPersoon")
	private Persoon idPersoon;

	/*
	 * @ManyToMany(targetEntity = Persoon.class) private Set personen;
	 */

	public Project() {
		// super();
		this.latitude = 0;
		this.longtitude = 0;
		this.goedgekeurd = false;
		this.QR = UUID.randomUUID().toString();

	}

	public Project(int idProject, String titel, String type, float latitude, float longtitude, boolean goedgekeurd,
			String QR, String beschrijving, Persoon idPersoon) {
		super();
		this.idProject = idProject;
		this.titel = titel;
		this.type = type;
		this.latitude = latitude;
		this.longtitude = longtitude;
		this.QR = QR;
		this.beschrijving = beschrijving;
		this.idPersoon = idPersoon;
	}

	public int getIdProject() {
		return idProject;
	}

	public void setIdProject(int idProject) {
		this.idProject = idProject;
	}

	public String getTitel() {
		return titel;
	}

	public void setTitel(String titel) {
		this.titel = titel;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public float getLatitude() {
		return latitude;
	}

	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	public float getLongtitude() {
		return longtitude;
	}

	public void setLongtitude(float longtitude) {
		this.longtitude = longtitude;
	}

	public boolean isGoedgekeurd() {
		return goedgekeurd;
	}

	public void setGoedgekeurd(boolean goedgekeurd) {
		this.goedgekeurd = goedgekeurd;
	}

	public String getBeschrijving() {
		return beschrijving;
	}

	public void setBeschrijving(String beschrijving) {
		this.beschrijving = beschrijving;
	}

	/*
	 * public Set getPersonen() { return personen; }
	 * 
	 * public void setPersonen(Set personen) { this.personen = personen; }
	 */

	public String getQR() {
		return QR;
	}

	public void setQR(String qR) {
		QR = qR;
	}

	public Persoon getIdPersoon() {
		return idPersoon;
	}

	public void setIdPersoon(Persoon idPersoon) {
		this.idPersoon = idPersoon;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

}
