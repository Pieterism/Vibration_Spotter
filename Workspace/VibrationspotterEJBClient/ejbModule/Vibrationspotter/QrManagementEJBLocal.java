package Vibrationspotter;

/**
 * Bean to handle QR class queries and operations on database
 * 
 * @author Birgen Vermang, Thomas Bruneel, Pieter-Jan Vanhaverbeke, Pieter
 *         Vanderhaegen
 *
 */
public interface QrManagementEJBLocal {

	/**
	 * @param text
	 *            string to convert to QR
	 * @param width
	 *            width of resulting QR image
	 * @param height
	 *            height of resulting QR image
	 * @return QR image as byte-array
	 */
	public byte[] getQRCodeImage(String text, int width, int height);

}
