package Vibrationspotter;

import java.util.List;

import model.Meting;

/**
 * Interface to handle Meting class queries and operations on database
 * 
 * @author Birgen Vermang, Thomas Bruneel, Pieter-Jan Vanhaverbeke, Pieter
 *         Vanderhaegen
 *
 */
public interface MetingManagementEJBLocal {

	/**
	 * @param titelMeting
	 *            title of the Meting
	 * @return the requested meting
	 */
	public Meting findMeting(String titelMeting);

	/**
	 * @param meting
	 *            Meting to add
	 */
	void addMeting(Meting meting);

	/**
	 * @param idProject
	 *            id of project to get metingen from
	 * @return all metingen from project with requested id
	 */
	public List<Meting> findMetingenByIdProject(int idProject);

	/**
	 * @param idProject
	 *            id of project to remove metingen
	 */
	public void wissenMetingenByProjectid(int idProject);

	/**
	 * @param JSONarray
	 *            results of the meting
	 */
	public void ToevoegenMetingResultaten(String JSONarray);

	/**
	 * @param id
	 *            id of project
	 * @return metingen in JSON format
	 */
	public String haalProjectMetingen(String id);

	/**
	 * @param idMeting
	 *            id meting
	 * @return result with time and acceleration
	 */
	public byte[] zoekDataset1(int idMeting);

	/**
	 * @param idMeting
	 *            id meting
	 * @return result with amplitude and frequency
	 */
	public byte[] zoekDataset2(int idMeting);

	/**
	 * @param idMeting
	 *            id of meting
	 */
	public void wissenMeting(int idMeting);

	/**
	 * @param idMeting
	 *            id of meting to find photo
	 * @return photo meting
	 */
	public byte[] zoekFoto(int idMeting);

	/**
	 * @param id
	 *            id meting
	 * @return requested meting
	 */
	public Meting findMetingById(int id);

	/**
	 * @param gegevens
	 *            results of meting
	 * @param id
	 *            id meting
	 */
	public void ToevoegenMetingResultaten2(String gegevens, String id);

}
