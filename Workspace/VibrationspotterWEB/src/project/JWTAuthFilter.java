package project;

import java.io.IOException;

import javax.annotation.Priority;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;

import com.sun.mail.iap.Response;

/*
@Priority(Priorities.AUTHENTICATION)
public class JWTAuthFilter implements ContainerRequestFilter{
  @Override
  public void filter(ContainerRequestContext requestContext) throws IOException {
        String authHeaderVal = requestContext.getHeaderString("Authorization");
            //consume JWT i.e. execute signature validation
            if(authHeaderVal.startsWith("Bearer")){
            try {
                validate(authHeaderVal.split(" ")[1]);
            } catch (InvalidJwtException ex) {
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            }
            }else{
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            }
  }
*/