package Vibrationspotter;

import model.Leerkracht;

/**
 * Interface to handle Leerkreacht class queries and operations on database
 * 
 * @author Birgen Vermang, Thomas Bruneel, Pieter-Jan Vanhaverbeke, Pieter
 *         Vanderhaegen
 *
 */
public interface LeerkrachtManagementEJBLocal {
	/**
	 * @param idLeerkracht
	 *            id of Leerkeracht you want to get
	 * @return the selected Leerkracht
	 */
	public Leerkracht findLeerkracht(int idLeerkracht);

	/**
	 * @param leerkracht
	 *            Leerkracht to add to database
	 */
	void addLeerkracht(Leerkracht leerkracht);

}
