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
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity 
@Table(name="Foto")
@NamedQuery(name="Foto.findAll", query="SELECT p FROM Foto p")

public class Foto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="idFoto", nullable=false)
	private int idFoto;
	
	@Lob
	@Column(length=100000)	//nog aanpassen
	private byte[] foto;
	
	@ManyToOne
	@JoinColumn(name = "idMeting")
	private Meting meting;

	public Foto(){
		idFoto = 0;
		foto = null;	
		meting = null;
	}
	
	public Foto(int idFoto, byte[] foto, Meting meting) {
		this.idFoto = idFoto;
		this.foto = foto;
		this.meting = meting;
	}

	public int getIdFoto() {
		return idFoto;
	}

	public void setIdFoto(int idFoto) {
		this.idFoto = idFoto;
	}

	public byte[] getFoto() {
		return foto;
	}

	public void setFoto(byte[] foto) {
		this.foto = foto;
	}

	public Meting getMeting() {
		return meting;
	}

	public void setMeting(Meting meting) {
		this.meting = meting;
	}
	
	
	
	
	
	
	
	
}
