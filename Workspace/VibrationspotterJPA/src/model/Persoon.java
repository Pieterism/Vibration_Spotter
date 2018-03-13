package model;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Set;
import Bcrypt.BCrypt;

import javax.persistence.*;

@Entity
@Table(name = "Persoon")
@SecondaryTable(name = "PersoonGroep", pkJoinColumns=@PrimaryKeyJoinColumn(name="idPersoon", referencedColumnName="idPersoon"))
@NamedQuery(name = "Persoon.findAll", query = "SELECT p FROM Persoon p")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(discriminatorType = DiscriminatorType.INTEGER, name = "idPersoon", columnDefinition = "TINYINT(1)")

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
	
	@Column(table="PersoonGroep", name="idGroep")
	private String groep;

	public Persoon() {
		paswoord = "amdin";
		salt=BCrypt.gensalt();
	}

	public Persoon(int idPersoon, String voornaam, String achternaam, String paswoord, String emailadres,boolean admin,String salt,Set projects) {
		super();
		this.salt = BCrypt.gensalt();
		this.idPersoon = idPersoon;
		this.voornaam = voornaam;
		this.achternaam = achternaam;
		this.emailadres = emailadres;
		System.out.println(paswoord);
		this.paswoord = hashPasswd(paswoord,salt);
		this.admin = admin;
		this.projects=projects;
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
		this.paswoord=hashPasswd(paswoord,salt);

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

	public void setSalt(String s) {
		salt = s;
	}

	public Set getProjects() {
		return projects;
	}

	public void setProjects(Set projects) {
		this.projects = projects;
	}

	public String hashPasswd(String passwd,String salt){
		return BCrypt.hashpw(passwd, salt);
	}
	

}
