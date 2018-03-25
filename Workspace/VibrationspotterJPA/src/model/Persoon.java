package model;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Set;
import Bcrypt.BCrypt;

import javax.persistence.*;

@Entity
@Table(name = "Persoon")
@NamedQuery(name = "Persoon.findAll", query = "SELECT p FROM Persoon p")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING, name = "type", columnDefinition = "TINYINT(1)")

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

	@Column(name = "emailadres", nullable = false, unique=true)
	private String emailadres;

	@Column(name = "admin", nullable = false)
	private boolean admin;

	@Column(name = "salt", nullable = false)
	private String salt;
	
	@Column(name = "type")
	private String type;
	
//	private Set<Project> projecten;		//nieuw
	
	/*  @ManyToMany
	  @JoinTable(name = "project_persoon", joinColumns = @JoinColumn(name = "Persoon_idPersoon", referencedColumnName = "idPersoon"), inverseJoinColumns = @JoinColumn(name = "Project_idProject", referencedColumnName = "idProject"))
	    public Set<Project> getProjecten() {
	        return projecten;
	        }
	  
	   public void setBooks(Set<Project> projecten) {
	        this.projecten = projecten;
	    }		*/							//nieuw deel 2
	
	
	
	//GOEDE OUDE
	@ManyToMany(targetEntity = Project.class)
	@JoinTable(
	      name="persoon_project",											//oude project_persoon
	    //  joinColumns = @JoinColumn(name = "Persoon_idPersoon"),
	    //  inverseJoinColumns=@JoinColumn(name="Project_idProject"))
	      joinColumns=@JoinColumn(name="Persoon_idPersoon", referencedColumnName="idPersoon"),
	      inverseJoinColumns=@JoinColumn(name="Project_idProject", referencedColumnName="idProject"))
	private Set projecten;
	
	

	/*@ManyToMany(targetEntity = Project.class)
	private Set projects;*/
	

	
	/*@ManyToMany
	public Set<Persoon> getCustomers();*/

	public Persoon() {
		
		type = "Leerkracht";
		paswoord = "admin";
		admin = false;
		salt=BCrypt.gensalt();
		projecten = new HashSet<Project>();
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
	//	this.projects=projects;
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

	public Set getProjecten() {
		return projecten;
	}

	public void setProjecten(Set projects) {
		this.projecten = projects;
	}
	

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String hashPasswd(String passwd,String salt){
		return BCrypt.hashpw(passwd, salt);
	}
	
	public void addProject(Project project){
		projecten.add(project);
	}
	

}
