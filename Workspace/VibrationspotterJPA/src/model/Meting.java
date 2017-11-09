package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the meting database table.
 * 
 */
@Entity
@NamedQuery(name="Meting.findAll", query="SELECT m FROM Meting m")
public class Meting implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private MetingPK id;

	private String locatie;

	private String resultaat;

	private String tijdstip;

	private String titel;

	private String type;

	//bi-directional many-to-one association to Persoon
	@ManyToOne
	private Persoon persoon;

	public Meting() {
	}

	public MetingPK getId() {
		return this.id;
	}

	public void setId(MetingPK id) {
		this.id = id;
	}

	public String getLocatie() {
		return this.locatie;
	}

	public void setLocatie(String locatie) {
		this.locatie = locatie;
	}

	public String getResultaat() {
		return this.resultaat;
	}

	public void setResultaat(String resultaat) {
		this.resultaat = resultaat;
	}

	public String getTijdstip() {
		return this.tijdstip;
	}

	public void setTijdstip(String tijdstip) {
		this.tijdstip = tijdstip;
	}

	public String getTitel() {
		return this.titel;
	}

	public void setTitel(String titel) {
		this.titel = titel;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Persoon getPersoon() {
		return this.persoon;
	}

	public void setPersoon(Persoon persoon) {
		this.persoon = persoon;
	}

}