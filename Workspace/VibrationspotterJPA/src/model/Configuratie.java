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
@Table(name = "configuratie")
@NamedQuery(name = "configuratie.findAll", query = "SELECT p FROM Configuratie p")

/*
 * Klasse waarbij we de Configuratiefiles bijhouden zoals keys, eventueel taal...
 */
public class Configuratie implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idConfiguratie", nullable = false)
	private int idConfiguratie;

	@Column(name = "taal")
	private String taal;

	public Configuratie() {
		super();
	}

	public Configuratie(int idConfiguratie, String taal) {
		super();
		this.idConfiguratie = idConfiguratie;
		this.taal = taal;
	}

	public int getIdConfiguratie() {
		return idConfiguratie;
	}

	public String getTaal() {
		return taal;
	}

	public void setTaal(String taal) {
		this.taal = taal;
	}

}