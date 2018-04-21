package project;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Vibrationspotter.MetingManagementEJBLocal;
import model.Meting;

@WebServlet("/image/*")
public class ImageServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@EJB
	 private MetingManagementEJBLocal metingejb;
	
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	HttpSession session = request.getSession();
    	int idMeting=(int) session.getAttribute("idMeting");

    		response.getOutputStream().write(metingejb.zoekFoto(idMeting));
    		

    	
    }


} 