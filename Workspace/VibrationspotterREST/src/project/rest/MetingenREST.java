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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import Vibrationspotter.LoginManagementEJBLocal;
import Vibrationspotter.MetingManagementEJBLocal;
import Vibrationspotter.SpotterManagementEJBLocal;

@Named
@ViewScoped
@Path("Metingen")
public class MetingenREST implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@EJB
	private MetingManagementEJBLocal metingEJB;
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	public String postTest(String jsonarray){
		if(jsonarray == null) throw new BadRequestException();
		System.out.println(jsonarray);
		
		metingEJB.ToevoegenMetingResultaten(jsonarray);
		
		return "[{\"Gelukt?:\": YEZZZ!!!}]";
	}
	
/*	@GET
	public Response getMeting(){
		System.out.println("Alle metingen verzenden.");
		return Response.ok().build();
	}*/

}
