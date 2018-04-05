package Vibrationspotter;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.google.gson.Gson;

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
	
	public void aanmakenSpotter(String json){
		Gson gson = new Gson();
	//	String jsonInString = "{\"userId\":\"1\",\"userName\":\"Yasir\"}";
		Spotter spotter= gson.fromJson(json, Spotter.class);
		
		spotter.setAdmin(false);
		spotter.setType("Spotter");
	}
	
	
	
	
}
