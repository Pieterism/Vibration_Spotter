package project;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import java.io.Serializable;

import javax.ejb.EJB;

import Vibrationspotter.LoginManagementEJBLocal;
import Vibrationspotter.MetingManagementEJBLocal;
import Vibrationspotter.PersonManagementEJBLocal;
import jdk.nashorn.internal.runtime.Context;
import model.Persoon;



//http://localhost:8080/VibrationspotterWEB/
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
	 //System.out.println(user);
	 //System.out.println(pwd);
	 boolean valid;
	 valid=loginEJB.controleerpaswoord(emailadres,pwd);
	 
	 //FacesContext context = FacesContext.getCurrentInstance();
	 //ExternalContext externalContext = context.getExternalContext();
	 Persoon user = personEJB.findPersoonByEmail(emailadres);
	 if(valid){
		 HttpSession session = SessionUtils.getSession();
		 session.setAttribute("emailadres", user);
		 session.setAttribute("idPersoon", user.getIdPersoon());
		 session.setAttribute("idPersoon", user.isAdmin());
		 
		 
		// externalContext.getSessionMap().put("Persoon", user);
		 return "/Home/index.xhtml?faces-redirect=true";
	 }
	 else{
		 FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"wachtwoord en gebruikersnaam komen niet overeen!","Please enter correct username and Password"));
		 return "login";
		 
	 }

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