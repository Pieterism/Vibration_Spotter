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
private String checkpwd1;
private String checkpwd2;

public String submit() {

	System.out.println(persoon.getEmailadres());
	if(!(persoonejb.findPersoonByEmail(persoon.getEmailadres())==null)){
		FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"iemand heeft al een account met dit emailadres","iemand heeft al een account met dit emailadres"));
		return null;
	}
	if(!checkpwd1.equals(checkpwd2)){
		FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"gelieve tweemaal hetzelfde passwoord in te geven","gelieve tweemaal hetzelfde passwoord in te geven"));
		return null;
	}
	else{
		persoon.setPaswoord(checkpwd1);
		persoonejb.addPersoon(persoon);
		FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"account is met succes aangemaakt","account is met succes aangemaakt"));
		return "createaccount";
	}
}

public Persoon getPersoon() {
	return persoon;
}

public void setPersoon(Persoon persoon) {
	this.persoon = persoon;
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
