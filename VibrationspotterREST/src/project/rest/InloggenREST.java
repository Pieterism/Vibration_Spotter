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

/**
 * Implementation of RESTful services for connection with database for login on
 * android device.
 * 
 * @author Birgen Vermang, Thomas Bruneel, Pieter-Jan Vanhaverbeke, Pieter
 *         Vanderhaegen
 *
 */
@Named
@ViewScoped
@Path("Inloggen")
public class InloggenREST implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private SpotterManagementEJBLocal spotterEJB;

	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	public String inloggen(String gegevens) {
		System.out.println(gegevens);
		if (spotterEJB == null)
			System.out.println("NULLL!");

		else {
			System.out.println("het werkt");
		}

		if (spotterEJB.checkInloggen(gegevens) == true) {

			System.out.println("je mag inloggen");

			return gegevens;
		} else {
			System.out.println("je mag niet inloggen");

			return "[{\"Inloggen\": Verkeerd!!!}]";

		}
	}
}