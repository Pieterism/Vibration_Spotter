package Vibrationspotter;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import model.Foto;
import model.Spotter;

@Named
@Stateless
public class SpotterManagementEJB implements SpotterManagementEJBLocal {

	@PersistenceContext(unitName = "demodb") // Aanpassen met xmlfile?
	private EntityManager em;

	@Resource
	private SessionContext ctx;

	@Override
	public Foto findFoto(int idFoto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addFoto(Foto foto) {
		// TODO Auto-generated method stub
		
	}
	
	public void aanmakenSpotter(String ingegevenstring){
		
		
		ingegevenstring = ingegevenstring.substring(1, ingegevenstring.length()-1);			//zorgen dat [] weg is
//		System.out.println(ingegevenstring);
		
		JSONObject json = null;
		
		try {
			json = new JSONObject(ingegevenstring);
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	Spotter spotter = new Spotter();
	try{
	spotter.setVoornaam(json.getString("voornaam"));
	spotter.setAchternaam(json.getString("achternaam"));
	spotter.setPaswoord(json.getString("paswoord"));
	spotter.setEmailadres(json.getString("emailadres"));
	spotter.setGebruikersnaam(json.getString("gebruikersnaam"));
	}
	catch (JSONException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	
	spotter.setAdmin(false);
	spotter.setType("Spotter");
	em.persist(spotter);
	

	}
	
	
	
	
}
