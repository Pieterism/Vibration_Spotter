package project.rest;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

import Vibrationspotter.MetingManagementEJBLocal;
import Vibrationspotter.PersonManagementEJBLocal;
import Vibrationspotter.ProjectManagementEJBLocal;

@Named
@ViewScoped
@Path("Projecten")
public class ProjectenRest implements Serializable {
private static final long serialVersionUID = 1L;
	
	@EJB
	private ProjectManagementEJBLocal projectEJB;
	
	@EJB
	private PersonManagementEJBLocal persoonEJB;
	
	@EJB
	private MetingManagementEJBLocal metingEJB;
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	public String postTest(String jsonarray){
		if(jsonarray == null) throw new BadRequestException();
		System.out.println(jsonarray);
		
		String test = projectEJB.FindAllProjectsForApp(jsonarray);
		//System.out.println("test " + test);
		return test;
	}
	
	@POST
	@Path("ToevoegenProjecten")
	public String VoegProjectToe (String jsonarray){
		projectEJB.ToevoegenProjectenAPP(jsonarray);
		System.out.println("projectaangemaakt");
		
		 return "[{\"Projectaanmaken\": Gelukt!!!}]";
	}
	
	@POST
	@Path("VerwijderenProjecten")
	public String VerwijderProjectViaAPP (String jsonarray){
		projectEJB.verwijderProjectViaApp(jsonarray);
	
		 return "[{\"Projectverwijderen\": Gelukt!!!}]";
	}
	
	@GET
	@Path( "Size/{projectID}")
	public String haalProjectensize (@PathParam("projectID") String id){
		//int size = projectEJB.haalMetingenSizeProject(Integer.parseInt(id));
		int size = metingEJB.findMetingenByIdProject(Integer.parseInt(id)).size();
		return "[{\"size\": " + size + "}]";
	}
	
	
	@POST
	@Path("ProjectViaQR")
	public String haalprojectviaQR (String jsonarray){
	String project =projectEJB.HaalprojectviaApp(jsonarray);
	
	System.out.println("project is gehaald via QR");
	
		 return project;
	}
	
	@GET
	@Path("alleProjecten")
	public String goedgekeurdeProjecten(){
		String resultaat = projectEJB.findGoedgekeurdeProjectenAPP();
		return resultaat;
	}
/*
 * @GET
	@Path( "AlleMetingen/{projectID}")
	public String haalMeting (@PathParam("projectID") String id){
		
		return metingEJB.haalProjectMetingen(id);
	}

 */
}
