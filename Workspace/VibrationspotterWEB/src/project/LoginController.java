package project;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import java.io.Serializable;

import javax.ejb.EJB;

import Vibrationspotter.LoginManagementEJBLocal;
import Vibrationspotter.MetingManagementEJBLocal;
import Vibrationspotter.PersonManagementEJBLocal;
import model.Persoon;



//http://localhost:8080/VibrationspotterWEB/
@Named
@ViewScoped
public class LoginController implements Serializable {

private static final long serialVersionUID = 1L;

@EJB
private LoginManagementEJBLocal loginEJB;
@EJB
private PersonManagementEJBLocal persoonejb;
 
 private String pwd;
 private String emailadres;
 private String msg;
 
 
 public String submit() {
	 boolean valid;
	 valid=loginEJB.controleerpaswoord(emailadres,pwd);
	 if(valid){
		 return "index";
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


public String getMsg() {
	return msg;
}

public void setMsg(String msg) {
	this.msg = msg;
}



public String getEmailadres() {
	return emailadres;
}


public void setEmailadres(String emailadres) {
	this.emailadres = emailadres;
}


  




}