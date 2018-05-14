package Vibrationspotter;

import model.Persoon;
import model.Spotter;

/**
 * Bean to handle Person class queries and operations on database
 * 
 * @author Birgen Vermang, Thomas Bruneel, Pieter-Jan Vanhaverbeke, Pieter
 *         Vanderhaegen
 *
 */
public interface PersonManagementEJBLocal {

	/**
	 * @param voornaam
	 *            front name
	 * @param achternaam
	 *            last name
	 * @return person with corresponding name
	 */
	Persoon findPersoon(String voornaam, String achternaam);

	/**
	 * @param p
	 *            person to add
	 */
	void addPersoon(Persoon p);

	/**
	 * @param emailadres
	 *            email address person
	 * @return person with corresponding email
	 */
	Persoon findPersoonByEmail(String emailadres);

	/**
	 * @param id
	 *            id person
	 * @return person with corresponding id
	 */
	Persoon findPersoonByid(int id);

	/**
	 * @param gebruikersnaam
	 *            username of user
	 * @return spotter with corresponding username
	 */
	Spotter findSpotterByGebruiksnaam(String gebruikersnaam);

	/**
	 * @param idPersoon
	 *            id of person
	 * @return email address of person
	 */
	String zoekEmailadres(int idPersoon);

	/**
	 * @param id
	 *            person id
	 * @return amount of projects from that person
	 */
	int haalProjectenSizeVanPersoon(int id);

	int getPersoonsize();

}
