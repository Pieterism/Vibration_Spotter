package project.rest;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.glassfish.jersey.server.ResourceConfig;

//import com.owlike.genson.ext.jaxrs.GensonJsonConverter;

@ApplicationPath("Restservice")
public class ApplicationConfig extends Application{
private final Set<Class<?>> classes;
	
	/*Aanmaken REST: koppeling tussen Databank en Android
	Iedere Restsklasse zorgt voor een verbinding
	 *
	 *
	 *MetingenREST geeft de meting door waarna er een grafiek kan gemaakt worden bij WEB.
	 *QRcodeREST geeft de String van de barcode door
	 *RegistratieRest maakt een Spotter aan van de aanmaakgegevens App
	 *InloggenRest vergelijkt passwoord en login met databank
	 *ProjectenREST vraagt de verschillende projecten van een gebruiker op.
	 *
	 */	


	public ApplicationConfig() {
		HashSet<Class<?>> c = new HashSet<>();
		c.add(MetingenREST.class);
		c.add(QRcodeREST.class);
		c.add(RegistratieREST.class);
		c.add(InloggenREST.class);
		c.add(ProjectenRest.class);
		c.add(FotoREST.class);
		c.add(PersoonREST.class);
		classes = Collections.unmodifiableSet(c);
	}
	
	@Override
	public Set<Class<?>> getClasses() {
		return classes;
	}
}
