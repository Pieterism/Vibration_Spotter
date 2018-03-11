package project.rest;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("restTest")
public class RestTest {
	
	@POST
	public Boolean postTest(){
		System.out.println("Test gelukt");
		return true;
	}
	
	@GET
	public Boolean getTest(){
		System.out.println("Joepie");
		return true;
	}

}
