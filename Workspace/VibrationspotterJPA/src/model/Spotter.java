package model;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "Spotter")
@NamedQuery(name = "Spotter.findAll", query = "SELECT p FROM Spotter p")
@PrimaryKeyJoinColumn(referencedColumnName = "idPersoon")
@DiscriminatorValue("Spotter")
public class Spotter extends Persoon {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "gebruikersnaam", nullable = false, unique = true)
	private String gebruikersnaam;

	public Spotter(){
		super();
	}

	public Spotter(String gebruikersnaam){
		super();
		this.gebruikersnaam = gebruikersnaam;
	}

	public String getGebruikersnaam() {
		return gebruikersnaam;
	}

	public void setGebruikersnaam(String gebruikersnaam) {
		this.gebruikersnaam = gebruikersnaam;
	}

}
