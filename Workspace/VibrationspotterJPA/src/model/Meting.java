package model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;



@Entity
@Table(name="Meting")
//@NamedQuery(name="Meting.findAll", query="SELECT m FROM Meting m")
public class Meting implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="idMeting", nullable=false)
	private int idMeting;
	
	@Column(name="locatie", nullable=false)
	private String locatie;
	
	@Column(name="paswoord", nullable=false)
	private String paswoord;
	
	@Column(name="titel", nullable=false)
	private String titel;
	
	public String getTitel() {
		return titel;
	}

	public void setTitel(String titel) {
		this.titel = titel;
	}

	public Meting() {
		locatie = null;
		paswoord = null;
	}

	public Meting(String locatie, String paswoord) {
		this.locatie = locatie;
		this.paswoord = paswoord;
	}


	public int getId() {
		return idMeting;
	}


	public String getLocatie() {
		return locatie;
	}

	public void setLocatie(String locatie) {
		this.locatie = locatie;
	}

	public String getPaswoord() {
		return paswoord;
	}

	public void setPaswoord(String paswoord) {
		this.paswoord = paswoord;
	}

	

}