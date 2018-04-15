package project;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.io.Serializable;

import javax.ejb.EJB;

import Vibrationspotter.MetingManagementEJBLocal;
import Vibrationspotter.PersonManagementEJBLocal;

import model.Meting;
import model.Persoon;
import model.Project;

import java.util.List;
import java.util.ArrayList;


//http://localhost:8080/VibrationspotterWEB/
@Named
@ViewScoped
public class MetingController implements Serializable {

private static final long serialVersionUID = 1L;

 @EJB
 private MetingManagementEJBLocal metingejb;
  
 @EJB
 private PersonManagementEJBLocal persoonejb;
  
  
 private Meting meting=new Meting();
 private Persoon persoon=new Persoon();
 
 private Project project=new Project();
 private List<Meting> metingen = new ArrayList<Meting>();
  

 public String submit() {
	metingejb.addMeting(meting);
	persoonejb.addPersoon(persoon);
	return "succes.xhtml";
 }
 
 
 public void findMetingenByIdProject(){
	 	HttpSession session = SessionUtils.getSession();
	 	int idProject = 0;
	 	if(session.getAttribute("idProject")!=null){
	 	 idProject = (int) session.getAttribute("idProject");
	 	}
	 	metingen=metingejb.findMetingenByIdProject(idProject);
		//metingen=metingejb.findMetingenByIdProject(project.getIdProject());
	}
 
 public void gaTerugNaarMetingen() throws IOException{
	 HttpSession session = SessionUtils.getSession();
	 int idProject=(int) session.getAttribute("idProject");
	 FacesContext.getCurrentInstance().getExternalContext().redirect("metingen.xhtml?id="+idProject);
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

public Project getProject() {
	return project;
}


public void setProject(Project project) {
	this.project = project;
}


public List<Meting> getMetingen() {
	return metingen;
}


public void setMetingen(List<Meting> metingen) {
	this.metingen = metingen;
}


}