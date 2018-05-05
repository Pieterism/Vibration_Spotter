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

/**
 * Implementation of RESTful services for connection with Meting database table.
 * 
 * @author Birgen Vermang, Thomas Bruneel, Pieter-Jan Vanhaverbeke, Pieter
 *         Vanderhaegen
 *
 */
@Named
@ViewScoped
@Path("Metingen")
public class MetingenREST implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private MetingManagementEJBLocal metingEJB;

	@POST
	@Path("{projectID}")
	@Consumes({ MediaType.APPLICATION_JSON })
	public String newMeting(String jsonarray, @PathParam("projectID") String id) {
		if (jsonarray == null)
			throw new BadRequestException();
		System.out.println("metingen verwerken");

		System.out.println(id);

		metingEJB.ToevoegenMetingResultaten2(jsonarray, id);
		return "[{\"Gelukt?:\": YEZZZ!!!}]";
	}

	@GET
	@Path("AlleMetingen/{projectID}")
	public String haalMeting(@PathParam("projectID") String id) {

		return metingEJB.haalProjectMetingen(id);
	}

}
