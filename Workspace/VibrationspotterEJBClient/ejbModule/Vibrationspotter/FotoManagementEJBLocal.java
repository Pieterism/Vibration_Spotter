package Vibrationspotter;

import model.Foto;

/**
 * Interface to handle Foto class queries and operations on database
 * 
 * @author Birgen Vermang, Thomas Bruneel, Pieter-Jan Vanhaverbeke, Pieter
 *         Vanderhaegen
 *
 */
public interface FotoManagementEJBLocal {
	/**
	 * @param idFoto
	 *            the id of the photo you want to get
	 * @return the selected photo
	 */
	public Foto findFoto(int idFoto);

	/**
	 * @param foto
	 *            the photo you want to add to your database
	 */
	void addFoto(Foto foto);

	/**
	 * @param gegevens
	 *            the string representation off the picture
	 */
	void doorsturenfoto(String gegevens);

}
