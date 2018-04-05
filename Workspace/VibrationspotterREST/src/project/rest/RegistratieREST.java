package project.rest;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import Vibrationspotter.SpotterManagementEJBLocal;

@Path("Registreren")
public class RegistratieREST {

	@EJB
	private SpotterManagementEJBLocal spotterEJB;
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	public String registreren(String gegevens){
		System.out.println(gegevens);
		
		spotterEJB.aanmakenSpotter(gegevens);
		
		return "[{\"Registratie\": Gelukt!!!}]";
	}
}
