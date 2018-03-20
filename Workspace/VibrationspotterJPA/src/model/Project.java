package model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity
@Table(name = "Project")
@NamedQuery(name = "Project.findAll", query = "SELECT p FROM Project p")
public class Project implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idProject", nullable = false)
	private int idProject;

	@Column(name = "titel", nullable = false)
	private String titel;

	@Column(name = "type", nullable = false)
	private String type; // aan elk type meting een int toekennen en adhv deze int
						// bepalen welk type het is.

	@Column(name = "latitude", nullable = false)
	private float latitude;

	@Column(name = "longtitude", nullable = false)
	private float longtitude;

	@Column(name = "goedgekeurd", nullable = false)
	private boolean goedgekeurd;

	@Column(name = "QR", nullable = false)
	private String QR;

	@Lob
	@Column(name = "beschrijving", length = 512)
	private String beschrijving;

	@ManyToMany(targetEntity = Persoon.class)
	private Set personen;

	public Project() {
		//super();
		this.latitude = 0;
		this.longtitude = 0;
		this.goedgekeurd = false;

	}

	public Project(int idProject, String titel, String type, float latitude, float longtitude, boolean goedgekeurd, String beschrijving, Set personen) {
		super();
		this.idProject = idProject;
		this.titel = titel;
		this.type = type;
		this.latitude = latitude;
		this.longtitude = longtitude;
		this.beschrijving = beschrijving;
		this.personen = personen;
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

	public Set getPersonen() {
		return personen;
	}

	public void setPersonen(Set personen) {
		this.personen = personen;
	}

}
