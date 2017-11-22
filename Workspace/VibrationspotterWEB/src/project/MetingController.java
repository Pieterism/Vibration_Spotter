package project;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ejb.EJB;

import Vibrationspotter.MetingManagementEJBLocal;
import Vibrationspotter.PersonManagementEJBLocal;

import model.Meting;
import model.Persoon;


//http://localhost:8080/VibrationspotterWEB/
@Named
@ViewScoped
public class MetingController {

  private String output="alle velden invullen";
  
  @EJB
  private MetingManagementEJBLocal metingejb;
  
  @EJB
  private PersonManagementEJBLocal persoonejb;
  
  
  private Meting meting=new Meting();
  private Persoon persoon=new Persoon();

  public String submit() {
	//output = m.getPersoon()+", uw project met titel "+m.getTitel()+" werd met succes aangemaakt!";
	 System.out.println(meting.getTitel());
	metingejb.addMeting(meting);
	persoonejb.addPersoon(persoon);
	
    setM(null);
    return "index.xhtml";
  }


  public String getOutput() {
    return output;
  }

  public void setOutput(String output) {
    this.output = output;
  }

public Meting getM() {
	return meting;
}

public void setM(Meting m) {
	this.meting = meting;
}


}