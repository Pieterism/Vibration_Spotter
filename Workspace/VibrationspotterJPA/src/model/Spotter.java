package model;

import java.io.Serializable;
import java.util.List;

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
@Table(name="Spotter")
@NamedQuery(name="Spotter.findAll", query="SELECT p FROM Spotter p")

public class Spotter implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="idSpotter", nullable=false)
	private int idSpotter;
	
	
	@Column(name="emailadres", nullable=false,unique=true)
	private int emailadres;
	
	@Lob
	@Column(length=100000)	//nog aanpassen
	private byte[] foto;
	
	/*
	@ManyToMany(mappedBy="spotters")
	private List<Project> projects;
	*/
}
