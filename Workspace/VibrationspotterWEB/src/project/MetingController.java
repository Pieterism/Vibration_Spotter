package project;

import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ejb.EJB;


//http://localhost:8080/testWEB/
@Named
@ViewScoped
public class MetingController {

  private String output="alle velden invullen";
  @EJB
  private MetingManagementEJB mmejb;
  
  private Meting m=new Meting();
  private Persoon p=new Persoon();

  public String submit() {
		output = m.getPersoon()+", uw project met titel "+m.getTitel()+" werd met succes aangemaakt!";
	//EJB aanroepen
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
	return m;
}

public void setM(Meting m) {
	this.m = m;
}

}