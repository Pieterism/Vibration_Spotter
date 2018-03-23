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

	private Project project = new Project();

	public String submit() {

		projectejb.addProject(project);
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Uw project is aangemaakt", "Uw project is aangemaakt"));

		return "index.xhtml";
	}

	public String wissen(Project pro) {
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

	public void aanvaardProject(Project pro) {
		pro.setGoedgekeurd(true);
	}

	public void keurProjectAf(Project pro) {
		pro.setGoedgekeurd(false);
	}

}
