package project.rest;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import Vibrationspotter.SpotterManagementEJBLocal;

@Named
@ViewScoped
@Path("Registreren")
public class RegistratieREST implements Serializable {
 
	private static final long serialVersionUID = 1L;

	@EJB
	private SpotterManagementEJBLocal spotterEJB;
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	public String registreren(String gegevens){
		System.out.println(gegevens);
		if(spotterEJB == null) System.out.println("NULLL!");
		
		else{
			System.out.println("het werkt");
		}
		
		spotterEJB.aanmakenSpotter(gegevens);
		
		return "[{\"Registratie\": Gelukt!!!}]";
	}
}
