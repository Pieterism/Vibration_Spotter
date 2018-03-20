package project.rest;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("restTest")
public class RestTest {
	
	
			
	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	public String postTest(String jsonarray){
		if(jsonarray == null) throw new BadRequestException();
		System.out.println(jsonarray);
	return "[{\"Gelukt?:\": YEZZZ!!!}]";
	}
	
	@GET
	public Response getMeting(){
		System.out.println("Alle metingen verzenden.");
		return Response.ok().build();
	}

}
