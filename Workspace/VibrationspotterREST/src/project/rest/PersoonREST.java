package project.rest;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import Vibrationspotter.ProjectManagementEJBLocal;

@Named
@ViewScoped
@Path("Persoon")
public class PersoonREST implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@EJB
	private ProjectManagementEJBLocal projectEJB;
	
	@GET
	@Path("/{idProject}")
	public String getPersoonVanProject(@PathParam("idProject")String idProject){
		
		projectEJB.findPersonByIdProject(Integer.parseInt(idProject));
	
		return projectEJB.findPersonByIdProject(Integer.parseInt(idProject));
	}


}
