package project;

import java.io.IOException;
import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import Vibrationspotter.MetingManagementEJBLocal;
import Vibrationspotter.PersonManagementEJBLocal;
import Vibrationspotter.ProjectManagementEJBLocal;
import model.Leerkracht;
import model.Persoon;
import model.Project;
import model.Spotter;

// gebruiker (leerkracht of spotter) aanmaken en info over gebruiker ophalen
@Named
@ViewScoped
public class UserController implements Serializable {

private static final long serialVersionUID = 1L;

@EJB
private PersonManagementEJBLocal persoonejb;

private Leerkracht leerkracht=new Leerkracht();
private Spotter spotter=new Spotter();
private Persoon persoon=new Persoon();
private String checkpwd1;
private String checkpwd2;

public String leerkrachtAccountAanmaken() {

	if(!(persoonejb.findPersoonByEmail(leerkracht.getEmailadres())==null)){
		FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"iemand heeft al een account met dit emailadres","iemand heeft al een account met dit emailadres"));
		return null;
	}
	if(!checkpwd1.equals(checkpwd2)){
		FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"gelieve tweemaal hetzelfde passwoord in te geven","gelieve tweemaal hetzelfde passwoord in te geven"));
		return null;
	}
	else{
		leerkracht.setPaswoord(checkpwd1);
		persoonejb.addPersoon(leerkracht);
		leerkracht.setType("Leerkracht");
		FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"account is met succes aangemaakt","account is met succes aangemaakt"));
		return "createaccount";
	}
}

public String spotterAccountAanmaken() {

	if(!(persoonejb.findPersoonByEmail(spotter.getEmailadres())==null)){
		FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"iemand heeft al een account met dit emailadres","iemand heeft al een account met dit emailadres"));
		return null;
	}

	if(!(persoonejb.findSpotterByGebruiksnaam(spotter.getGebruikersnaam())==null)){
		FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"iemand heeft al een account met deze gebruikersnaam","iemand heeft al een account met deze gebruiksnaam"));
		return null;
	}

	if(!checkpwd1.equals(checkpwd2)){
		FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"gelieve tweemaal hetzelfde passwoord in te geven","gelieve tweemaal hetzelfde passwoord in te geven"));
		return null;
	}
	else{
		spotter.setPaswoord(checkpwd1);
		persoonejb.addPersoon(spotter);
		leerkracht.setType("Leerkracht");
		FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"account is met succes aangemaakt","account is met succes aangemaakt"));
		return "createaccount";
	}
}

public void toonGegevens(Project pro) throws IOException{
	HttpSession session = SessionUtils.getSession();
	session.setAttribute("temp", pro.getIdPersoon().getIdPersoon());
	System.out.println("hallo");
	FacesContext.getCurrentInstance().getExternalContext().redirect("persoon.xhtml");

}
public Persoon ophalenPersoon(){
	HttpSession session = SessionUtils.getSession();
	int idPersoon=(int) session.getAttribute("temp");
	Persoon p=persoonejb.findPersoonByid(idPersoon);
	System.out.println(idPersoon);
	return p;
	
}

public Persoon getPersoon() {
	return persoon;
}

public void setPersoon(Persoon persoon) {
	this.persoon = persoon;
}

public Persoon getSpotter() {
	return spotter;
}

public void setSpotter(Spotter spotter) {
	this.spotter = spotter;
}

public Persoon getLeerkracht() {
	return leerkracht;
}



public void setLeerkracht(Leerkracht leerkracht) {
	this.leerkracht = leerkracht;
}



public String getCheckpwd1() {
	return checkpwd1;
}

public void setCheckpwd1(String checkpwd1) {
	this.checkpwd1 = checkpwd1;
}

public String getCheckpwd2() {
	return checkpwd2;
}

public void setCheckpwd2(String checkpwd2) {
	this.checkpwd2 = checkpwd2;
}


}
