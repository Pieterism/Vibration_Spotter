package model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Meting")
// @NamedQuery(name="Meting.findAll", query="SELECT m FROM Meting m")
public class Meting implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idMeting", nullable = false)
	private int idMeting;

	@Column(name = "titel", nullable = false)
	private String titel;

	@Column(name = "tijdstip", nullable = false)
	private String tijdstip;
	
	@Column(name = "foto", nullable = false)
	private String foto;

	@Lob
	@Column(name = "resultaten", nullable = false) // NOG VERWIJDEREN
	private String resultaten;

	@Lob
	@Column(name = "opmerking", length = 512)
	private String opmerking;

	@ManyToOne
	@JoinColumn(name = "idProject")
	private Project idProject;

	public Meting() {
		super();
	}

	public Meting(int idMeting, String titel, String tijdstip, String resultaten, String opmerking, Project idProject) {
		super();
		this.idMeting = idMeting;
		this.titel = titel;
		this.tijdstip = tijdstip;
		this.resultaten = resultaten;
		this.opmerking = opmerking;
		this.idProject = idProject;
	}

	public int getIdMeting() {
		return idMeting;
	}

	public void setIdMeting(int idMeting) {
		this.idMeting = idMeting;
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

	public String getResultaten() {
		return resultaten;
	}

	public void setResultaten(String resultaten) {
		this.resultaten = resultaten;
	}

	public String getOpmerking() {
		return opmerking;
	}

	public void setOpmerking(String opmerking) {
		this.opmerking = opmerking;
	}

	public Project getIdProject() {
		return idProject;
	}

	public void setIdProject(Project idProject) {
		this.idProject = idProject;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}
	

}