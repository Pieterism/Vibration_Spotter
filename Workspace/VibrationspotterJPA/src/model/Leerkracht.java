package model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity 
@Table(name="Leerkracht")
@NamedQuery(name="Leerkracht.findAll", query="SELECT p FROM Leerkracht p")

public class Leerkracht implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="idLeerkracht", nullable=false,unique=true)
	private int idLeerkracht;
	
	@Column(name="email", nullable=false,unique=true)
	private String email;
	
	@Column(name="studentPaswoord", nullable=false)
	private String studentPaswoord;
	
	@Column(name="schooladres", nullable=false)
	private String schooladres;
	
	@OneToMany(mappedBy = "LeerkrachtProject") 
	private List<Project> projecten;
	
	@OneToMany(mappedBy = "leerkracht")
	private List<Student> studentPaswoorden;

	public Leerkracht() {
	}
	

	public Leerkracht(int idLeerkracht, String email, String studentPaswoord, String schooladres,
			List<Project> projecten) {
		super();
		this.idLeerkracht = idLeerkracht;
		this.email = email;
		this.studentPaswoord = studentPaswoord;
		this.schooladres = schooladres;
		this.projecten = projecten;
	}



	public int getIdLeerkracht() {
		return idLeerkracht;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	public String getSchooladres() {
		return schooladres;
	}

	public void setSchooladres(String schooladres) {
		this.schooladres = schooladres;
	}


	public String getStudentPaswoord() {
		return studentPaswoord;
	}


	public void setStudentPaswoord(String studentPaswoord) {
		this.studentPaswoord = studentPaswoord;
	}

	
	
	
	

}
