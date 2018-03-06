package project;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import Vibrationspotter.MetingManagementEJBLocal;
import Vibrationspotter.ProjectManagementEJBLocal;
import model.Project;

@Named
@ViewScoped
public class ProjectController implements Serializable {

private static final long serialVersionUID = 1L;

@EJB
private ProjectManagementEJBLocal projectejb;

private Project project=new Project();

public String submit() {
System.out.println("aanmaken");
projectejb.addProject(project);
FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"UW project is aangemaakt","Please enter correct username and Password"));
return "index.xhtml";
}

public void wissen(){
	System.out.println("wissen");
	projectejb.RemoveProject(project);
}

public Project getProject() {
	return project;
}

public void setProject(Project project) {
	this.project = project;
}



}
