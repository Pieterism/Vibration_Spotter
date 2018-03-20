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
	
	//final ResourceConfig config = new ResourceConfig().register(GensonJsonConverter.class);

	public ApplicationConfig() {
		HashSet<Class<?>> c = new HashSet<>();
		c.add(RestTest.class);
		classes = Collections.unmodifiableSet(c);
	}
	
	@Override
	public Set<Class<?>> getClasses() {
		return classes;
	}
}
