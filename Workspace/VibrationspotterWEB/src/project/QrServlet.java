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
import Vibrationspotter.ProjectManagementEJBLocal;
import Vibrationspotter.QrManagementEJBLocal;
import model.Project;
//QR van het project weergeven op de webapp
@WebServlet("/qr/*")
public class QrServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	@EJB
	private QrManagementEJBLocal qrejb;
	
	@EJB
	private ProjectManagementEJBLocal projectejb;
	
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	HttpSession session = request.getSession();
    	int idProject=(int) session.getAttribute("idProject");
    	String text;
    	Project p=projectejb.findProjectById(idProject);
    	text=p.getQR();
        response.getOutputStream().write(qrejb.getQRCodeImage(text, 800, 800));
    }


} 