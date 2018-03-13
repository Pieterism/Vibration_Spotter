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

public String submit() {
persoonejb.addPersoon(persoon);
return "createaccount.xhtml";
}

public Persoon getPersoon() {
	return persoon;
}

public void setPersoon(Persoon persoon) {
	this.persoon = persoon;
}

}
