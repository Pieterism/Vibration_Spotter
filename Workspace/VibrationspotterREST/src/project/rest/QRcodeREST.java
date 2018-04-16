package project.rest;

import java.io.IOException;
import java.io.Serializable;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import Vibrationspotter.ProjectManagementEJBLocal;

@Path("QR")
public class QRcodeREST implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@EJB
	private ProjectManagementEJBLocal projectEJB;
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	public String qr_accept(String qr) throws IOException{
		String waarde;
		
		if(projectEJB.checkQR(qr)==true){
			waarde = "true";
		}
		else{
			waarde = "false";
		}
		
		
		System.out.println(qr);
		return "waarde";
	}
	
}
