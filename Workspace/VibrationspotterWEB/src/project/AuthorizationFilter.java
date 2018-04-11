package project;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter(filterName = "AuthFilter", urlPatterns = { "/Home/*", "/Admin/*", "/Spotter/*", "/Leerkracht/*"})
public class AuthorizationFilter implements Filter {
//	"/metingen.xhtml/*"
	public AuthorizationFilter() {
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
		String reqURI = ((HttpServletRequest) request).getRequestURI();
		boolean toegang = false;
		
	    if (((HttpServletRequest) request).getSession().getAttribute("idPersoon") == null) {
	    	toegang = false;
	    	((HttpServletResponse) response).sendRedirect("/VibrationspotterWEB/login.xhtml");
	    	
	    	// User is not logged in. Redirect to login page.
	 
	    } else {
	    	if(reqURI.indexOf("/Admin")>0){	
	    		if((boolean) ((HttpServletRequest) request).getSession().getAttribute("admin")){
	    			toegang = true;
	    		}
	    		else{
	    			toegang = false;
	    		}
	    		
	    		
	    	}
	    	
	/*    	else if (reqURI.indexOf("/metingen.xthml")>0){
	    		(boolean) ((HttpServletRequest) request).getSession().getAttribute("admin")
	    	}*/
	    	
	    	//gewone gebruiker die attribute heeft
	    	
	    	else if((reqURI.indexOf("/Leerkracht")>0)){
	    		if((boolean) (((HttpServletRequest) request).getSession().getAttribute("type")).equals("Leerkracht")){
	    			toegang = true;
	    		}
	    		else{
	    			toegang = false;
	    		}
	    		
	    	}
	    	
	    	else if((reqURI.indexOf("/Spotter")>0)){
	    		if((boolean) (((HttpServletRequest) request).getSession().getAttribute("type")).equals("Spotter")){
	    			toegang = true;
	    		}
	    		else{
	    			toegang = false;
	    		}
	    		
	    	}

	        // User is logged in. Just continue with request.
	    	if(toegang){
	    	 chain.doFilter(request, response);
	    	}
	    	else{
	    	      ((HttpServletResponse) response).sendRedirect("/VibrationspotterWEB/login.xhtml");
	    	}
	      
	    }
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
	
	

	/*@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		try {

			HttpServletRequest reqt = (HttpServletRequest) request;
			HttpServletResponse resp = (HttpServletResponse) response;
			HttpSession ses = reqt.getSession(false);

			String reqURI = reqt.getRequestURI();
			
			//inlog uitzetten kan door door deze if te vervangen door "chain.doFilter(request, response);"

			System.out.println("Huidige sessie: admin="+ses.getAttribute("admin")+"; emailadres="+ses.getAttribute("emailadres"));;
			
		    if((ses != null && ses.getAttribute("username") != null)){ //checkt of er ingelogd is
				if(reqURI.indexOf("/Home")>0){						// checkt of het een matchid pagina is (waar enkel matchid toegang mag tot hebben) doet dit aan dehand van  de mappenstructuur van de xhtml bestanden
					if(ses.getAttribute("admin").equals("admin")){				// checkt of het een matchid account is
						chain.doFilter(request, response);				// in dit geval mag de request door gaaan
						return;
					}else{
						resp.sendRedirect("/");		//anders wordt er verwezen naar de loginpagina
						System.out.println("redirect-------------------------------------------------------");
						return;
					}
				}else{
					chain.doFilter(request, response);		
					return;
				}
			}else{
				if(reqURI.indexOf("/")>0){						
					resp.sendRedirect("/");
					System.out.println("redirect-------------------------------------------------------");
					return;
				}else{
					chain.doFilter(request, response);		
					return;
				}

			}
		    
// tot hier vervangen, wel niet verwijderen
		    
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void destroy() {

	}*/
}
