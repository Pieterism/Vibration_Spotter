package project;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import java.io.Serializable;

import javax.ejb.EJB;

import Vibrationspotter.MetingManagementEJBLocal;
import Vibrationspotter.PersonManagementEJBLocal;

import model.Meting;
import model.Persoon;


//http://localhost:8080/VibrationspotterWEB/
@Named
@ViewScoped
public class MetingController implements Serializable {

private static final long serialVersionUID = 1L;

private String output="alle velden invullen";

  
  @EJB
  private MetingManagementEJBLocal metingejb;
  
  @EJB
  private PersonManagementEJBLocal persoonejb;
  
  
  private Meting meting=new Meting();
  private Persoon persoon=new Persoon();

  public String submit() {
	setOutput(persoon.getGebruikersnaam()+", uw project met titel "+meting.getTitel()+" werd met succes aangemaakt!");
	System.out.println(persoon.getGebruikersnaam()+", uw project met titel "+meting.getTitel()+" werd met succes aangemaakt!");
	metingejb.addMeting(meting);
	persoonejb.addPersoon(persoon);
	return "/index.xhtml?faces-redirect=true";
  }


  public String getOutput() {
    return output;
  }

  public void setOutput(String output) {
    this.output = output;
  }

public Meting getMeting() {
	return meting;
}

public void setMeting(Meting meting) {
	this.meting = meting;
}

public Persoon getPersoon() {
	return persoon;
}

public void setPersoon(Persoon persoon) {
	this.persoon = persoon;
}


}