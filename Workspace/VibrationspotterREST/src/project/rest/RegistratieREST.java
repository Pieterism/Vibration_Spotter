package project.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

@Path("Registreren")
public class RegistratieREST {
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	public String registreren(String gegevens){
		System.out.println(gegevens);
		return "[{\"Registratie\": Gelukt!!!}]";
	}

}
