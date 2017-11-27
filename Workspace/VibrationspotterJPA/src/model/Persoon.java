
package model;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity 
@Table(name="Persoon")
@NamedQuery(name="Persoon.findAll", query="SELECT p FROM Persoon p")
//@SecondaryTable(name = "Person_Group", pkJoinColumns=@PrimaryKeyJoinColumn(name="login", referencedColumnName="login"))
public class Persoon implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id", nullable=false, length=16)
	private int id;

	@Column(name="gebruikersnaam", nullable=false)
	private String gebruikersnaam;

	@Column(name="paswoord", nullable=false)
	private String paswoord;


	public Persoon(){

	}

	public Persoon(String gebruikersnaam, String paswoord) {
		this.gebruikersnaam = gebruikersnaam;
		this.paswoord = paswoord;
	}



	public int getId() {
		return id;
	}

	public String getGebruikersnaam() {
		return gebruikersnaam;
	}

	public void setGebruikersnaam(String gebruikersnaam) {
		this.gebruikersnaam = gebruikersnaam;
	}

	public String getPaswoord() {
		return paswoord;
	}

	public void setPaswoord(String paswoord) {
		this.paswoord = paswoord;
	}	
}

