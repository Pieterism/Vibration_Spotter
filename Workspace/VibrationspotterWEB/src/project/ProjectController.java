package project;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
	

	private Project project = new Project();

	public String submit() {
      HttpSession session = SessionUtils.getSession();
	  int id = (int)session.getAttribute(("idPersoon"));
	  Persoon	user = personejb.findPersoonByid(id);
	  project.setIdPersoon(user);
	  projectejb.addProject(project);
	  FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO, "Uw project is aangemaakt", "Uw project is aangemaakt"));

		return "index.xhtml";
	 }
	
	public String submitSpotter() {
	    HttpSession session = SessionUtils.getSession();
		int id = (int)session.getAttribute(("idPersoon"));
		Persoon	user = personejb.findPersoonByid(id);
		project.setIdPersoon(user);
		project.setType("brug");
		projectejb.addProject(project);
		FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO, "Uw project is aangemaakt", "Uw project is aangemaakt"));

			return "index.xhtml";
		}
		public String submitStem() {
		    HttpSession session = SessionUtils.getSession();
			int id = (int)session.getAttribute(("idPersoon"));
			Persoon	user = personejb.findPersoonByid(id);
			project.setIdPersoon(user);
			project.setType("kraan");
			projectejb.addProject(project);
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO, "Uw project is aangemaakt", "Uw project is aangemaakt"));

				return "index.xhtml";
			}

	public String wissen(Project pro) {
		System.out.println(pro.getIdProject());
		projectejb.wissenProject(pro.getIdProject());
		return "projecten.xhtml";
	}
	
	public void gaNaarMeting(Project pro) throws IOException{
		HttpSession session = SessionUtils.getSession();
		int idproject = pro.getIdProject();
		session.setAttribute("idProject", idproject);		
		FacesContext.getCurrentInstance().getExternalContext().redirect("metingen.xhtml?id="+idproject);
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
	
	public List<Project> geefProjectenweer(){
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
