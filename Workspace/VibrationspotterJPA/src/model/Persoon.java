package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "Persoon")
@NamedQuery(name = "Persoon.findAll", query = "SELECT p FROM Persoon p")
@Inheritance(strategy = InheritanceType.JOINED)
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

	@Column(name = "admin", nullable = false)
	private boolean admin;

	@Column(name = "paswoord", nullable = false)
	private String paswoord;

	@ManyToMany
	@JoinTable(name = "Persoon_has_Project", joinColumns = @JoinColumn(name = "Persoon", referencedColumnName = "idPersoon"), inverseJoinColumns = @JoinColumn(name = "Project", referencedColumnName = "IdProject"))
	private List<Project> projects = new ArrayList<>();

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

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public String getPaswoord() {
		return paswoord;
	}

	public void setPaswoord(String paswoord) {
		this.paswoord = paswoord;
	}

	public List<Project> getProjects() {
		return projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}

	public void addProject(Project p) {
		projects.add(p);
	}

	public void removeProject(Project p) {
		projects.remove(p);
	}

}
