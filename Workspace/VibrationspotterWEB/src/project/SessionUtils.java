package project;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

public class SessionUtils {
	public static HttpSession getSession() {
		return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
	}
	
	public static int getUserId() {
		HttpSession session = getSession();
		if (session != null)
			return (int) session.getAttribute("idPersoon");
		else
			return 0;
	}
	
}
