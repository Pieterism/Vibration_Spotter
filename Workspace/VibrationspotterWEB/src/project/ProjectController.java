package project;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import Vibrationspotter.MetingManagementEJBLocal;
import Vibrationspotter.PersonManagementEJBLocal;
import Vibrationspotter.ProjectManagementEJBLocal;
import model.Persoon;
import model.Project;


@Named
@ViewScoped
public class ProjectController implements Serializable {

private static final long serialVersionUID = 1L;

@EJB
private ProjectManagementEJBLocal projectejb;

@EJB
private PersonManagementEJBLocal personejb;

private Project project=new Project();

private Persoon user;

public String submit() {
	HttpSession session = SessionUtils.getSession();
	//user = personejb.findPersoonByid((int) session.getAttribute(("idPersoon")));  
	int id = (int)session.getAttribute(("idPersoon"));
	user = personejb.findPersoonByid(id);
	
	project.setIdPersoon(user);
	//System.out.println("PJ: " + user.getIdPersoon());
	//user = personejb.findPersoonByid(session.getAttribute(("idPersoon")));  
	//user = personejb.findPersoonByid(Integer.parseInt(session.getId())); 

	//project.getPersonen().add(user);
	//user.getProjects().add(project);
	
	//OUDE GOEDE
	
//	project.addPersonen(user);
//	user.addProject(project);
	
	
	//LOGGING
	
//	System.out.println(project.getPersonen().size());
//	System.out.println(project.getPersonen().toString());
	/*   for (Iterator<Persoon> it = project.getPersonen().iterator(); it.hasNext(); ) {
	        Persoon f = it.next();
	       System.out.println(f.getEmailadres()); 
	      
	    }*/
	

	

projectejb.addProject(project);
FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"Uw project is aangemaakt","Uw project is aangemaakt"));


return "index.xhtml";
}

public String wissen(Project pro){
	System.out.println(pro.getIdProject());
	projectejb.wissenProject(pro.getIdProject());
	return "projecten.xhtml";
}

public Project getProject() {
	return project;
}

public void setProject(Project project) {
	this.project = project;
}

/*public List<Project> geefProjectenweer(){
	boolean aanwezig = false;
	List<Project> projecten = projectejb.findAllProjecten();
	HttpSession session = SessionUtils.getSession();
	String email = session.getAttribute(("emailadres")).toString();
	
	for (int i=0; i<projecten.size();i++){
		Set personen = projecten.get(i).getPersonen();
		for (Iterator<Persoon> it = personen.iterator(); it.hasNext(); ) {
			Persoon p = it.next();
			if (p.getEmailadres().equals(email)){
				aanwezig = true;
		}
		
	}
	    if(!aanwezig){
	    	projecten.remove(i);
	    }
	    aanwezig = false;        
	}
	
	return projecten;
}*/

/*public List<Project> geefProjectenweer2(){
//	boolean aanwezig = false;
//	List<Persoon> personen = personejb.findAllPersons();
	Persoon gebruiker;
	HttpSession session = SessionUtils.getSession();
	int id = (int) session.getAttribute(("idPersoon"));
	gebruiker = personejb.findPersoonByid(id);
	System.out.println("id: " + gebruiker.getIdPersoon());
	List<Project> projecten = new ArrayList<Project>();
	System.out.println("setgrootte:" + gebruiker.getProjecten().size());
	projecten.addAll(gebruiker.getProjecten());
	System.out.println(projecten.size());
	
	
	return projecten;
}*/

public List<Project> geefProjectenweer2(){
//	boolean aanwezig = false;
//	List<Persoon> personen = personejb.findAllPersons();
	Persoon gebruiker;
	HttpSession session = SessionUtils.getSession();
	int id = (int) session.getAttribute(("idPersoon"));
	List<Project> projecten = projectejb.findAllProjecten();
	List<Project> goedeprojecten =  new ArrayList<Project>();
	
	for(int i=0;i<projecten.size();i++){
		if(projecten.get(i).getIdPersoon().getIdPersoon()==id){
			goedeprojecten.add(projecten.get(i));
		}
	}
	
	return goedeprojecten;
}


}
