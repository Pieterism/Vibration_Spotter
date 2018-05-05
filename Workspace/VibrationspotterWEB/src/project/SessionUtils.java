package project;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 * Class to simplify working with httpsessions.
 * 
 * @author Birgen Vermang, Thomas Bruneel, Pieter-Jan Vanhaverbeke, Pieter
 *         Vanderhaegen
 *
 */
public class SessionUtils {
	public static HttpSession getSession() {
		return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
	}
}
