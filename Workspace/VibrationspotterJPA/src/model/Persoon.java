package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "Persoon")
@NamedQuery(name = "Persoon.findAll", query = "SELECT p FROM Persoon p")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(
		discriminatorType = DiscriminatorType.INTEGER,
		name = "idPersoon",
		columnDefinition = "TINYINT(1)")


public class Persoon implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idPersoon", nullable = false, length = 16)
	private int idPersoon;

	@Column(name = "voornaam", nullable = false)
	private String voornaam;

	@Column(name = "achternaam", nullable = false)
	private String achternaam;

	@Column(name = "paswoord", nullable = false)
	private String paswoord;

	@Column(name = "emailadres", nullable = false)
	private String emailadres;

	@Column(name = "admin", nullable = false)
	private boolean admin;
	
	@Column(name = "salt", nullable = false)
	private String salt;

	@ManyToMany(targetEntity = Project.class)
	private Set projects;

	public Persoon() {
		super();
	}

	public Persoon(int idPersoon, String voornaam, String achternaam, String paswoord, String emailadres, boolean admin,
			Set projects) {
		super();
		this.idPersoon = idPersoon;
		this.voornaam = voornaam;
		this.achternaam = achternaam;
		this.paswoord = paswoord;
		this.emailadres = emailadres;
		this.admin = admin;
		this.projects = projects;
	}

	public int getIdPersoon() {
		return idPersoon;
	}

	public void setIdPersoon(int idPersoon) {
		this.idPersoon = idPersoon;
	}

	public String getVoornaam() {
		return voornaam;
	}

	public void setVoornaam(String voornaam) {
		this.voornaam = voornaam;
	}

	public String getAchternaam() {
		return achternaam;
	}

	public void setAchternaam(String achternaam) {
		this.achternaam = achternaam;
	}

	public String getPaswoord() {
		return paswoord;
	}

	public void setPaswoord(String paswoord) {
		this.paswoord = paswoord;
	}

	public String getEmailadres() {
		return emailadres;
	}

	public void setEmailadres(String emailadres) {
		this.emailadres = emailadres;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
	
	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public Set getProjects() {
		return projects;
	}

	public void setProjects(Set projects) {
		this.projects = projects;
	}

	// nog methodes nodig om met projecten om te gaan

}
