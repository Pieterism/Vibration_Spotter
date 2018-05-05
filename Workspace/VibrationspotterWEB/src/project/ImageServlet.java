package project;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Vibrationspotter.MetingManagementEJBLocal;

// afbeelding van de meting weergeven op de webapp
/**
 * Class to show images on the webpage.
 * 
 * @author Birgen Vermang, Thomas Bruneel, Pieter-Jan Vanhaverbeke, Pieter
 *         Vanderhaegen
 *
 */
@WebServlet("/image/*")
public class ImageServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@EJB
	private MetingManagementEJBLocal metingejb;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		int idMeting = (int) session.getAttribute("idMeting");

		response.getOutputStream().write(metingejb.zoekFoto(idMeting));

	}

}