package project.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

@Path("QR")
public class QRcodeREST {
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	public String qr_accept(String qr){
		System.out.println(qr);
		return "[{\"Status\": Gelukt!!!}]";
	}
	
}
