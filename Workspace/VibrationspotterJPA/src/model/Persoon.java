package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the persoon database table.
 * 
 */
@Entity
@NamedQuery(name="Persoon.findAll", query="SELECT p FROM Persoon p")
public class Persoon implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int idPersoon;

	private String achternaam;

	private String voornaam;

	//bi-directional many-to-one association to Meting
	@OneToMany(mappedBy="persoon")
	private List<Meting> metings;

	public Persoon() {
	}

	public int getIdPersoon() {
		return this.idPersoon;
	}

	public void setIdPersoon(int idPersoon) {
		this.idPersoon = idPersoon;
	}

	public String getAchternaam() {
		return this.achternaam;
	}

	public void setAchternaam(String achternaam) {
		this.achternaam = achternaam;
	}

	public String getVoornaam() {
		return this.voornaam;
	}

	public void setVoornaam(String voornaam) {
		this.voornaam = voornaam;
	}

	public List<Meting> getMetings() {
		return this.metings;
	}

	public void setMetings(List<Meting> metings) {
		this.metings = metings;
	}

	public Meting addMeting(Meting meting) {
		getMetings().add(meting);
		meting.setPersoon(this);

		return meting;
	}

	public Meting removeMeting(Meting meting) {
		getMetings().remove(meting);
		meting.setPersoon(null);

		return meting;
	}

}