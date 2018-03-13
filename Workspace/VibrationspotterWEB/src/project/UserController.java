package project;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import Vibrationspotter.MetingManagementEJBLocal;
import Vibrationspotter.PersonManagementEJBLocal;
import Vibrationspotter.ProjectManagementEJBLocal;
import model.Persoon;

@Named
@ViewScoped
public class UserController implements Serializable {

private static final long serialVersionUID = 1L;

@EJB
private PersonManagementEJBLocal persoonejb;

private Persoon persoon=new Persoon();
private String checkpwd;

public String submit() {
	if(persoon.getPaswoord().equals(checkpwd)){
		FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"gelieve tweemaal hetzelfde passwoord in te geven","gelieve tweemaal hetzelfde passwoord in te geven"));
		return null;
	}
	else{
		persoonejb.addPersoon(persoon);
		FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"account is met succes aangemaakt","account is met succes aangemaakt"));
		return null;
	}
}

public Persoon getPersoon() {
	return persoon;
}

public void setPersoon(Persoon persoon) {
	this.persoon = persoon;
}

public String getCheckpwd() {
	return checkpwd;
}

public void setCheckpwd(String checkpwd) {
	this.checkpwd = checkpwd;
}



}
