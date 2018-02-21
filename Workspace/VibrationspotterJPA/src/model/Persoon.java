
package model;
//PERSOON NOG AANPASSEN

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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
	@Column(name="idPersoon", nullable=false, length=16)
	private int idPersoon;
	
	@Column(name="gebruikersnaam", nullable=false) //Nog aanpassen
	private String gebruikersnaam;
	
	@Column(name="voornaam", nullable=false)
	private String voornaam;
	
	@Column(name="achternaam", nullable=false)
	private String achternaam;
	
	@Column(name="admin", nullable=false)
	private boolean admin;

	@Column(name="paswoord", nullable=false)
	private String paswoord;

	@OneToMany(mappedBy="persoon")
	private List<Leerkracht> leerkrachten;
	
	@OneToMany(mappedBy="persoon")
	private List<Student> studenten;
	
	@OneToMany(mappedBy="persoon")
	private List<Spotter> personen;

	public Persoon(){
	}

	//AANPASSEN CONSTRUCTOR

	public int getIdPersoon() {
		return idPersoon;
	}

	public String getPaswoord() {
		return paswoord;
	}

	public void setPaswoord(String paswoord) {
		this.paswoord = paswoord;
	}

	public String getVoornaam() {
		return voornaam;
	}

	public void setVoornaam(String voornaam) {
		this.voornaam = voornaam;
	}

	public String getAchternaam() {
		return achternaam;
	}

	public void setAchternaam(String achternaam) {
		this.achternaam = achternaam;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public String getGebruikersnaam() {
		return gebruikersnaam;
	}

	public void setGebruikersnaam(String gebruikersnaam) {
		this.gebruikersnaam = gebruikersnaam;
	}	
	
	
	
	
}

