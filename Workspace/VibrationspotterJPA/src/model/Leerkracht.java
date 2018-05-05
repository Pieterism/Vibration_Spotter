package model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * This class implements a database Leerkracht table and the getters and setters
 * for the database values.
 * 
 * @author Birgen Vermang, Thomas Bruneel, Pieter-Jan Vanhaverbeke, Pieter
 *         Vanderhaegen
 *
 */
@Entity
@Table(name = "leerkracht")
@NamedQuery(name = "leerkracht.findAll", query = "SELECT p FROM Leerkracht p")
@PrimaryKeyJoinColumn(referencedColumnName = "idPersoon")
@DiscriminatorValue("Leerkracht")
public class Leerkracht extends Persoon {

	private static final long serialVersionUID = 1L;

	@Column(name = "schoolnaam", nullable = false)
	private String schoolnaam;

	@Column(name = "schooladres", nullable = false)
	private String schooladres;

	public Leerkracht() {
		super();
	}

	public Leerkracht(String schoolnaam, String schooladres) {
		super();
		this.schoolnaam = schoolnaam;
		this.schooladres = schooladres;
	}

	public String getSchoolnaam() {
		return schoolnaam;
	}

	public void setSchoolnaam(String schoolnaam) {
		this.schoolnaam = schoolnaam;
	}

	public String getSchooladres() {
		return schooladres;
	}

	public void setSchooladres(String schooladres) {
		this.schooladres = schooladres;
	}

}
