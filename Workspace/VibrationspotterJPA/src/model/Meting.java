package model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Meting")
/*
 * Meting heeft gegevens met titel, opmerkingen...
 * Ook een foto toevoegen aan de meting is mogelijk.
 * Er worden ook nog 2 datasets aangemaakt voor de verwerking van de grafieken (met filter en zonder).
 */
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

	@Lob
	@Column(name = "foto", nullable = false)
	private byte[] foto;

	@Lob
	@Column(name = "resultaten", nullable = false) // NOG VERWIJDEREN
	private String resultaten;

	@Lob
	@Column(name = "opmerking", length = 512)
	private String opmerking;

	@Lob
	@Column(name = "dataset1")
	private byte[] dataset1;

	@Lob
	@Column(name = "dataset2")
	private byte[] dataset2;

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

	public byte[] getFoto() {
		return foto;
	}

	public void setFoto(byte[] foto) {
		this.foto = foto;
	}

	public byte[] getDataset1() {
		return dataset1;
	}

	public void setDataset1(byte[] dataset1) {
		this.dataset1 = dataset1;
	}

	public byte[] getDataset2() {
		return dataset2;
	}

	public void setDataset2(byte[] dataset2) {
		this.dataset2 = dataset2;
	}



}