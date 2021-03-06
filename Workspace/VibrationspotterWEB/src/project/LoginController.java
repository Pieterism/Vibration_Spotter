package project;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import Vibrationspotter.LoginManagementEJBLocal;
import Vibrationspotter.PersonManagementEJBLocal;
import model.Persoon;

/**
 * Class to implement login functionality on webpage.
 * 
 * @author Birgen Vermang, Thomas Bruneel, Pieter-Jan Vanhaverbeke, Pieter
 *         Vanderhaegen
 *
 */

@Named
@ViewScoped
public class LoginController implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private LoginManagementEJBLocal loginEJB;

	@EJB
	private PersonManagementEJBLocal personEJB;

	private String pwd;
	private String emailadres;
	private String msg;

	public String submit() {
		/*
		 * Kijkt of email en paswoord geldig is, indien wel wordt er
		 * doorverwezen naar de webpagina en worden de attributen meegegeven aan
		 * de sessie. Indien ongeldig zal je terug naar de login verwezen worden
		 * met een melding van ongeldig login.
		 */
		boolean valid;
		valid = loginEJB.controleerpaswoord(emailadres, pwd);

		// FacesContext context = FacesContext.getCurrentInstance();
		// ExternalContext externalContext = context.getExternalContext();
		Persoon user = personEJB.findPersoonByEmail(emailadres);
		// System.out.println("PJ HULP: " + user.getIdPersoon());
		if (valid) {
			HttpSession session = SessionUtils.getSession();
			session.setAttribute("emailadres", user);
			session.setAttribute("idPersoon", user.getIdPersoon());
			session.setAttribute("admin", user.isAdmin());
			session.setAttribute("idProject", 0);
			session.setAttribute("idMeting", 0);

			session.setAttribute("typePersoon", user.getType());

			if ((boolean) session.getAttribute("admin")) {

				return "/Admin/index.xhtml?faces-redirect=true"; // als admin,
																	// redirect
																	// naar
																	// admin
			}

			else if (((String) session.getAttribute("typePersoon")).equals("Leerkracht")) {

				return "/Spotter/index.xhtml?faces-redirect=true"; // als
																	// leerkracht,
																	// redirect
																	// naar
																	// leerkracht

			}

			else if (((String) session.getAttribute("typePersoon")).equals("Spotter")) {

				return "/Spotter/index.xhtml?faces-redirect=true"; // als
																	// spotter,
																	// redirect
																	// naar
																	// spotter

			}

			return null;

			/*
			 * else{ return "/Home/index.xhtml?faces-redirect=true"; //a,anders
			 * gewone pagina }
			 */

		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					"wachtwoord en gebruikersnaam komen niet overeen!", "Please enter correct username and Password"));
			return "/Home/login";

		}

	}

	public String logout() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "/login.xhtml?faces-redirect=true";
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getEmailadres() {
		return emailadres;
	}

	public void setEmailadres(String emailadres) {
		this.emailadres = emailadres;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}