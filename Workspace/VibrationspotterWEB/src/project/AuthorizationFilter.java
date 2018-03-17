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

@WebFilter(filterName = "AuthFilter", urlPatterns = { "/Home/*" })
public class AuthorizationFilter implements Filter {

	public AuthorizationFilter() {
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
	    if (((HttpServletRequest) request).getSession().getAttribute("idPersoon") == null) {
	        // User is not logged in. Redirect to login page.
	        ((HttpServletResponse) response).sendRedirect("/VibrationspotterWEB/login.xhtml");
	    } else {
	        // User is logged in. Just continue with request.
	        chain.doFilter(request, response);
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