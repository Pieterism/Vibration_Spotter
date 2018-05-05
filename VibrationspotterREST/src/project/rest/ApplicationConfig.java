package project.rest;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * This class implements the config for the REST service. We make a connection
 * between a database and our android device.
 * 
 * @author Birgen Vermang, Thomas Bruneel, Pieter-Jan Vanhaverbeke, Pieter
 *         Vanderhaegen
 *
 */
@ApplicationPath("Restservice")
public class ApplicationConfig extends Application {
	private final Set<Class<?>> classes;

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
