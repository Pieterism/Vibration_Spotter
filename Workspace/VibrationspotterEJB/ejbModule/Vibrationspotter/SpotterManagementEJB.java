package Vibrationspotter;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import model.Foto;
import model.Spotter;


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
	
	public Spotter aanmakenSpotter(String j){
		
	//	Gson gson = new Gson();
	//	String jsonInString = "{\"userId\":\"1\",\"userName\":\"Yasir\"}";
		
		

		
	//	Spotter spotter= gson.fromJson(json, Spotter.class);
		
		
	//	JSONObject jsonObj = new JSONObject("{\"phonetype\":\"N95\",\"cat\":\"WP\"}");
		
		JSONObject json = null;
		try {
			json = new JSONObject(j);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(json.get("voornaam"));
	//	String statistics = json.getString("voornaam");
	//	JSONObject voornaam = json.getJSONObject("voornaam");
	//	String ageJohn = name1.getString("Age");

	//	spotter.setAdmin(false);
	//	spotter.setType("Spotter");
	//	em.persist(spotter);
		
		return null;
	}
	
	
	
	
}
