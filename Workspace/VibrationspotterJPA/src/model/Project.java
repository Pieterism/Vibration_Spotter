package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity 
@Table(name="Project")
@NamedQuery(name="Project.findAll", query="SELECT p FROM Project p")
public class Project implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="idProject", nullable=false)
	private int idProject;
	
	@Column(name="titel", nullable=false)
	private String titel;

	@Column(name="latitude", nullable=false)		//float heeft scale en precission
	private float latitude;
	
	@Column(name="longtitude", nullable=false)		//float heeft scale en precission
	private float longtitude;
	
	@Lob
	@Column(name="beschrijving", length=512)	//length nog aanpassen
	private String beschrijving;
	
	@Column(name="resultaat")
	private int resultaat;
	
	@OneToMany(mappedBy = "project")
	private List<Meting> metingen;
	
	@ManyToOne
	@JoinColumn(name = "idLeerkracht")
	private Leerkracht LeerkrachtProject;

	@ManyToMany (mappedBy = "persoon")
	  private List<Persoon> deelnemers = new ArrayList<>(); 

}



//Getters, Setters, constructors