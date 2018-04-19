package project.rest;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import Vibrationspotter.FotoManagementEJBLocal;
import Vibrationspotter.SpotterManagementEJBLocal;

@Named
@ViewScoped
@Path("Foto")
public class FotoREST implements Serializable {
 
	private static final long serialVersionUID = 1L;

	@EJB
	private FotoManagementEJBLocal fotoEJB;
	
	@EJB
	private SpotterManagementEJBLocal spotterEJB;
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	public void inloggen(String gegevens){
		//fotoEJB.doorsturenfoto(gegevens);
		System.out.println("doorgestuurd Foto");
		System.out.println(gegevens);
		}
}
