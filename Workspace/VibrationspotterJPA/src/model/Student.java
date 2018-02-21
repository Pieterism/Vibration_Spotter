package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity 
@Table(name="Student")
@NamedQuery(name="Student.findAll", query="SELECT p FROM Student p")

public class Student {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="idStudent", nullable=false)
	private int idStudent;	
	
	@ManyToOne
	@JoinColumn(name = "idLeerkracht",nullable=false)
	private Leerkracht leerkracht;
	
	@ManyToOne
	@JoinColumn(name = "studentPaswoord",nullable=false)		
	private Leerkracht paswoord;
	

	public int getIdStudent() {
		return idStudent;
	}

	public void setIdStudent(int idStudent) {
		this.idStudent = idStudent;
	}
	
	
	
	
	
	
}
