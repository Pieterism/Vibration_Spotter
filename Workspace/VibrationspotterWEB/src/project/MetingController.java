package project;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import model.Meting;
//http://localhost:8080/testWEB/
@ManagedBean
@RequestScoped
public class MetingController {

  private String output="alle velden invullen";
  private Meting m=new Meting();

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