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
import model.Meting;
import model.Persoon;
import model.Project;

// project aanmaken, info over project ophalen en projecten wissen

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
		String typePersoon=(String) session.getAttribute("typePersoon");
		if(typePersoon.equals("Spotter")){
			project.setType("Vibrationspotter");
		}
		else if(typePersoon.equals("Leerkracht")){
			project.setType("STEM"); 
		}
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

	public void aanvaardProject(Project pro) {
		pro.setGoedgekeurd(true);
	}

	public void keurProjectAf(Project pro) {
		pro.setGoedgekeurd(false);
	}
	
	public List<Project> geefProjectenweer(){
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
	
	public void update(Project pro){
		System.out.println("update");
		System.out.println(pro.isGoedgekeurd());
		if(pro.isGoedgekeurd()==false){
			pro.setGoedgekeurd(true);
		}
		else{
			pro.setGoedgekeurd(false);
		}
		projectejb.update(pro);

	}
	public String zoekEmailadres(Project pro){
		String emailadres=(String) personejb.zoekEmailadres(pro.getIdPersoon().getIdPersoon());
		return emailadres;

	}
	
	public Project zoekProject(){
		HttpSession session = SessionUtils.getSession();
		int id = (int) session.getAttribute(("idProject"));
		Project p=projectejb.findProjectById(id);
		return p;
		

	}
	
	public List<Project> findMijnProjecten(){
		HttpSession session = SessionUtils.getSession();
		int idPersoon = (int) session.getAttribute(("idPersoon"));
		List<Project>projecten=projectejb.findMijnProjecten(idPersoon);
		
		return projecten;
		
	}
	
	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}
	
	public String toonQr(Project p){
		System.out.println("hallo");
		HttpSession session = SessionUtils.getSession();
		int idproject=p.getIdProject();
		session.setAttribute("idProject", idproject);
		return "qr.xhtml";	
	}
	
	public String checkGoedkeuring(Project p){
		if(p.isGoedgekeurd()==true){
			return "Ja";
		}
		return "Nee";
		
	}
	public List<Project> findProjectsByIdPerson(){
		HttpSession session = SessionUtils.getSession();
		int idPersoon=(int) session.getAttribute("temp");
		List<Project>projecten=projectejb.findMijnProjecten(idPersoon);
		return projecten;
		
	}


}
