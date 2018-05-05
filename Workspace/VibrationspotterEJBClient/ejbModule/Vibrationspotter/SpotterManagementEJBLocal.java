package Vibrationspotter;

import model.Foto;

/**
 * Bean to handle Person class queries and operations on database
 * 
 * @author Birgen Vermang, Thomas Bruneel, Pieter-Jan Vanhaverbeke, Pieter
 *         Vanderhaegen
 *
 */
public interface SpotterManagementEJBLocal {
	/**
	 * @param idFoto
	 *            id photo
	 * @return photo with corresponding id
	 */
	public Foto findFoto(int idFoto);

	/**
	 * @param foto
	 *            photo to add
	 */
	void addFoto(Foto foto);

	/**
	 * @param json
	 *            json representation of spotter attributes
	 */
	void aanmakenSpotter(String json);

	/**
	 * @param ingegevenstring
	 *            json representation of login credentials
	 * @return true if login credentials are correct
	 */
	boolean checkInloggen(String ingegevenstring);
}
