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

/**
 * This class implements a database Foto table and the getters and setters for
 * the database values.
 * 
 * @author Birgen Vermang, Thomas Bruneel, Pieter-Jan Vanhaverbeke, Pieter
 *         Vanderhaegen
 *
 */
@Entity
@Table(name = "foto")
@NamedQuery(name = "foto.findAll", query = "SELECT p FROM Foto p")
public class Foto implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idFoto", nullable = false)
	private int idFoto;

	@Lob
	@Column(name = "foto")
	private byte[] foto;

	@ManyToOne
	@JoinColumn(name = "idMeting")
	private Meting meting;

	/**
	 * default constructor
	 */
	public Foto() {
		super();
	}

	/**
	 * @param idFoto
	 *            id photo
	 * @param foto
	 *            byte array representation of the photo
	 * @param meting
	 *            the meting where the photo belongs to
	 */
	public Foto(int idFoto, byte[] foto, Meting meting) {
		super();
		this.idFoto = idFoto;
		this.foto = foto;
		this.meting = meting;
	}

	/**
	 * @return photo id
	 */
	public int getIdFoto() {
		return idFoto;
	}

	/**
	 * @param idFoto
	 */
	public void setIdFoto(int idFoto) {
		this.idFoto = idFoto;
	}

	/**
	 * @return the foto
	 */
	public byte[] getFoto() {
		return foto;
	}

	/**
	 * @param foto
	 *            photo
	 */
	public void setFoto(byte[] foto) {
		this.foto = foto;
	}

	/**
	 * @return the meting
	 */
	public Meting getMeting() {
		return meting;
	}

	/**
	 * @param meting
	 */
	public void setMeting(Meting meting) {
		this.meting = meting;
	}

}