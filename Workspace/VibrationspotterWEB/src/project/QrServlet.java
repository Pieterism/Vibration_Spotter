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
import Vibrationspotter.QrManagementEJBLocal;

@WebServlet("/qr/*")
public class QrServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	@EJB
	private QrManagementEJBLocal qrejb;
	
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	String text = (String) request.getParameter("id");
        response.getOutputStream().write(qrejb.getQRCodeImage(text, 500, 500));
    }










    
    
/*
	private int getId() {
		HttpSession session = SessionUtils.getSession();
		int id=(int) session.getAttribute("idMeting");
		return id;
	}
	*/

} 