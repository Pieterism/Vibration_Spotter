package Vibrationspotter;

/**
 * Interface to handle Login class queries and operations on database
 * 
 * @author Birgen Vermang, Thomas Bruneel, Pieter-Jan Vanhaverbeke, Pieter
 *         Vanderhaegen
 *
 */
public interface LoginManagementEJBLocal {

	/**
	 * @param user
	 *            username for login
	 * @param pwd
	 *            password to check
	 * @return true if password is correct
	 */
	boolean controleerpaswoord(String user, String pwd);

	/**
	 * @param email
	 *            email from the Leerkracht
	 * @param pwd
	 *            password
	 * @return true if password is correct
	 */
	boolean controleerPaswoordLeerkrachtApp(String email, String pwd);

}
