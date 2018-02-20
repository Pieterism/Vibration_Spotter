package model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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

	@Column(name="titel", nullable=false)
	private String titel;
	
	@Column(name="tijdstip", nullable=false)
	private String tijdstip;
	
	@Column(name="type", nullable=false)
	private String type;
	
	@Column(name="resultaat", nullable=false)//NOG VERWIJDEREN
	private String resultaat;

	@ManyToOne
	@JoinColumn(name = "idProject")
	private Project project;

	public Meting() {
		locatie = null;
		titel=null;
		type=null;
		tijdstip=null;	
		project=null;
	}

	public Meting(String locatie,String titel,String type,String tijdstip, Project project) {
		this.locatie = locatie;
		this.titel=titel;
		this.type=type;
		this.tijdstip=tijdstip;
		this.project=project;
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

	public String getTitel() {
		return titel;
	}

	public void setTitel(String titel) {
		this.titel = titel;
	}

	public String getTijdstip() {
		return tijdstip;
	}

	public void setTijdstip(String tijdstip) {
		this.tijdstip = tijdstip;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public String getResultaat() {
		return resultaat;
	}

	public void setResultaat(String resultaat) {
		this.resultaat = resultaat;
	}
	
	
	
	
	

}