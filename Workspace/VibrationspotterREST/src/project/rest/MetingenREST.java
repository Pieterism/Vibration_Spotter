package project.rest;

import javax.ejb.EJB;
import javax.ws.rs.BadRequestException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sun.tools.xjc.reader.xmlschema.bindinfo.BIConversion.User;

import Vibrationspotter.LoginManagementEJBLocal;
import Vibrationspotter.SpotterManagementEJBLocal;

@Path("Metingen")
public class MetingenREST {
	
	@EJB
	private LoginManagementEJBLocal loginEJB;
	
	@EJB
	private SpotterManagementEJBLocal spotterEJB;
	
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	public String postTest(String jsonarray){
		if(jsonarray == null) throw new BadRequestException();
		spotterEJB.aanmakenSpotter(jsonarray);
		
		System.out.println(jsonarray);
		
		return "[{\"Gelukt?:\": YEZZZ!!!}]";
	}
	
	@GET
	public Response getMeting(){
		System.out.println("Alle metingen verzenden.");
		return Response.ok().build();
	}

}
