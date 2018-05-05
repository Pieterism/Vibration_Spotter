package model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * Database table representation class. Table keeps some configuration info for
 * the application.
 * 
 * For example: language, validity period for QR code, amount of photos for each
 * meting,...
 * 
 * @author Birgen Vermang, Thomas Bruneel, Pieter-Jan Vanhaverbeke, Pieter
 *         Vanderhaegen
 *
 */
@Entity
@Table(name = "configuratie")
@NamedQuery(name = "configuratie.findAll", query = "SELECT p FROM Configuratie p")
public class Configuratie implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idConfiguratie", nullable = false)
	private int idConfiguratie;

	@Column(name = "taal")
	private String taal;

	/**
	 * default constructor
	 */
	public Configuratie() {
		super();
	}

	/**
	 * @param idConfiguratie
	 *            id of configuration
	 * @param taal
	 *            language
	 */
	public Configuratie(int idConfiguratie, String taal) {
		super();
		this.idConfiguratie = idConfiguratie;
		this.taal = taal;
	}

	/**
	 * @return the id of the configuration
	 */
	public int getIdConfiguratie() {
		return idConfiguratie;
	}

	/**
	 * @return the language
	 */
	public String getTaal() {
		return taal;
	}

	/**
	 * @param taal
	 *            set the language
	 */
	public void setTaal(String taal) {
		this.taal = taal;
	}

}