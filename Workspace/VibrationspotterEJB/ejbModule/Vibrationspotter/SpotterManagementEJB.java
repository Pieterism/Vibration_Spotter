package Vibrationspotter;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.json.JSONException;
import org.json.JSONObject;

import model.Foto;
import model.Spotter;

/**
 * Bean to handle Spotter class queries and operations on database
 * 
 * @author Birgen Vermang, Thomas Bruneel, Pieter-Jan Vanhaverbeke, Pieter
 *         Vanderhaegen
 *
 */
@Named
@Stateless
public class SpotterManagementEJB implements SpotterManagementEJBLocal {

	@EJB
	private LoginManagementEJBLocal loginEJB;

	@PersistenceContext(unitName = "demodb") // Aanpassen met xmlfile?
	private EntityManager em;

	@Resource
	private SessionContext ctx;

	/*
	 * (non-Javadoc)
	 * 
	 * @see Vibrationspotter.SpotterManagementEJBLocal#findFoto(int)
	 */
	@Override
	public Foto findFoto(int idFoto) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Vibrationspotter.SpotterManagementEJBLocal#addFoto(model.Foto)
	 */
	@Override
	public void addFoto(Foto foto) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * Vibrationspotter.SpotterManagementEJBLocal#aanmakenSpotter(java.lang.
	 * String)
	 */
	public void aanmakenSpotter(String ingegevenstring) {

		ingegevenstring = ingegevenstring.substring(1, ingegevenstring.length() - 1);

		JSONObject json = null;

		try {
			json = new JSONObject(ingegevenstring);
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Spotter spotter = new Spotter();
		try {
			spotter.setVoornaam(json.getString("voornaam"));
			spotter.setAchternaam(json.getString("achternaam"));
			spotter.setPaswoord(json.getString("paswoord"));
			spotter.setEmailadres(json.getString("emailadres"));
			spotter.setGebruikersnaam(json.getString("gebruikersnaam"));
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		spotter.setAdmin(false);
		spotter.setType("Spotter");
		em.persist(spotter);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Vibrationspotter.SpotterManagementEJBLocal#checkInloggen(java.lang.
	 * String)
	 */
	public boolean checkInloggen(String ingegevenstring) {

		boolean valid;
		String pwd = null;
		String email = null;
		String type = null;

		ingegevenstring = ingegevenstring.substring(1, ingegevenstring.length() - 1);
		JSONObject json = null;

		try {
			json = new JSONObject(ingegevenstring);
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			pwd = json.getString("paswoord");
			email = json.getString("email");
			type = json.getString("type");

		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (type.equals("leerkracht")) {
			valid = loginEJB.controleerPaswoordLeerkrachtApp(email, pwd);
			System.out.println("leerkrachtcontrole");
		} else {
			valid = loginEJB.controleerpaswoord(email, pwd);
		}

		return valid;

	}

}
