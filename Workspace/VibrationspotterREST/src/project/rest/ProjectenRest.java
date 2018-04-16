package project.rest;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import Vibrationspotter.MetingManagementEJBLocal;
import Vibrationspotter.ProjectManagementEJBLocal;

@Named
@ViewScoped
@Path("Projecten")
public class ProjectenRest implements Serializable {
private static final long serialVersionUID = 1L;
	
	@EJB
	private ProjectManagementEJBLocal projectEJB;
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	public String postTest(String jsonarray){
		if(jsonarray == null) throw new BadRequestException();
		System.out.println(jsonarray);
		
		
		
		return projectEJB.FindAllProjectsForApp(jsonarray);
	}

}
