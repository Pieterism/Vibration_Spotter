package project.rest;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("restTest")
public class RestTest {
	
	@POST
	public String postTest(){
		System.out.println("Test gelukt");
		return "GELUKT!";
	}
	
	@GET
	public String getTest(){
		System.out.println("Joepie");
		return "GELUKT!";
	}

}
