package model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity 
@Table(name="Configuratie")
@NamedQuery(name="Configuratie.findAll", query="SELECT p FROM Configuratie p")

public class Configuratie implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="idConfiguratie", nullable=false)
	private int idConfiguratie;

	public Configuratie() {	
	}
	
	public Configuratie(int idConfiguratie){
		this.idConfiguratie=idConfiguratie;
	}

	public int getIdConfiguratie() {
		return idConfiguratie;
	}	
	
}


//NOG AANPASSEN MET TAAL,...
