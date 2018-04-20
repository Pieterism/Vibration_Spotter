package Vibrationspotter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.json.JSONException;
import org.json.JSONObject;

import model.Foto;
import model.Meting;
import model.Persoon;
import sun.misc.BASE64Decoder;

@Stateless
public class FotoManagementEJB implements FotoManagementEJBLocal {

	@PersistenceContext(unitName = "demodb")
	private EntityManager em;

	@Resource
	private SessionContext ctx;

	@Override
	public Foto findFoto(int idFoto) {
		Query q = em.createQuery("SELECT p FROM Foto  WHERE p.idFoto = : idFoto");
		q.setParameter(1, idFoto);
		return null;
	}

	@Override
	public void addFoto(Foto foto) {
		// TODO Auto-generated method stub

	}
	
	public void doorsturenfoto(String gegevens){
		gegevens = gegevens.substring(1, gegevens.length()-1);	
		JSONObject json = null;
		String foto = null;
		try {
			 json = new JSONObject(gegevens);
			 foto = json.getString("image");
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		
		int idMeting = 43;
		Query q = em.createQuery("SELECT p FROM Meting p WHERE p.idMeting = :idMeting");
		q.setParameter("idMeting", idMeting);
		List<Meting> metingen = q.getResultList();
	/*	List<Meting> metingen2 = new ArrayList<Meting>();
		for(int i=0; i<metingen2.size();i++){
			if(metingen.get(i).getIdMeting()==43){
				metingen2.add(metingen.get(i));
			}
		}*/
		Meting meting;
		if (metingen.size() != 1) {
			meting = null;
		} else {
		 meting = metingen.get(0);
		}
		
		
		//foto van base 64 omzetten naar bytes.
		byte[] imageByte = null; 
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			imageByte = decoder.decodeBuffer(foto);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		meting.setFoto(imageByte);
		em.persist(meting);
	}

}
