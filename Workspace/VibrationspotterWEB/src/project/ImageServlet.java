package project;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Vibrationspotter.MetingManagementEJBLocal;

@WebServlet("/image/*")
public class ImageServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@EJB
	 private MetingManagementEJBLocal metingejb;
	
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	int id = (int) Long.parseLong(request.getParameter("id"));
        response.getOutputStream().write(metingejb.zoekFoto(id));
    }










    
    
/*
	private int getId() {
		HttpSession session = SessionUtils.getSession();
		int id=(int) session.getAttribute("idMeting");
		return id;
	}
	*/

} 