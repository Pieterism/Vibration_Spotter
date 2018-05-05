package Vibrationspotter;

/**
 * Bean to handle management of the Octave file
 * 
 * @author Birgen Vermang, Thomas Bruneel, Pieter-Jan Vanhaverbeke, Pieter
 *         Vanderhaegen
 *
 */
public interface OctaveManagementEJBLocal {

	/**
	 * @param s
	 *            data from accelerometer
	 * @return 2 datasets with results
	 */
	String[] createdata(String s);

	/**
	 * @param invoer
	 *            data to process
	 * @return result of processing with octave
	 */
	String verwerkstringdata(String invoer);

}
