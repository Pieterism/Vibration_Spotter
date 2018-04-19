package Vibrationspotter;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.json.JSONException;
import org.json.JSONObject;

import com.sun.jmx.snmp.Timestamp;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import model.Meting;
import model.Project;
import model.Spotter;

@Named
@Stateless
public class MetingManagementEJB implements MetingManagementEJBLocal{

	@PersistenceContext(unitName="demodb")
	private EntityManager em;

	@EJB
	private PersonManagementEJBLocal personEJB;
	
	@EJB
	private OctaveManagementEJBLocal octaveEJB;
	
	@EJB
	private ProjectManagementEJBLocal projectEJB;

	@Resource
	private SessionContext ctx;

	public MetingManagementEJB(){};

	@Override
	public Meting findMeting(String titelMeting) {
		//Methode zoeken van meting met bepaalde titel.
		Query q = em.createQuery("SELECT id FROM Meting m WHERE m.titel = :titel");
		q.setParameter(1, titelMeting);

		return em.find(Meting.class, titelMeting);
	}

	@Override
	public void addMeting(Meting meting) {
		em.persist(meting);
	}
	
	
	public List<Meting> findMetingenByIdProject(int idProject){
		Query q = em.createQuery("SELECT m FROM Meting m WHERE m.idProject.idProject= :fname");
		q.setParameter("fname", idProject);
		List<Meting> metingen = q.getResultList();
		return metingen;
	}
	
	public void nieuweMeting(String jsonArray){
		Project p = projectEJB.findProject("tom");
		Meting m = new Meting(0,"testV1","20/03/2018",jsonArray,"opmerking",p);
		addMeting(m);
	}
	
	public void wissenMetingenByProjectid(int idProject){
		Query q = em.createQuery("SELECT m FROM Meting m WHERE m.idProject.idProject= :id");
		q.setParameter("id", idProject);
		List<Meting> metingen = q.getResultList();
		for(Meting m:metingen){
			Meting met = em.find(Meting.class, m.getIdMeting());
			em.remove(met);
			
		}
		
	}
	
	public void ToevoegenMetingResultaten(String jsonarray){
		/*
		 * Methode die Resultaten gekregen van APP verwerkt en dan doorstuurt naar databank.
		 * De verwerking gebeurt in de OctaveManagementEJB klassen opgeroepen door verwerkstringdata(String data)
		 * en CreateData(String s)
		 */
		
		jsonarray = jsonarray.substring(1, jsonarray.length()-1);	
	//	JSONObject json = null;
		
		int index = jsonarray.lastIndexOf("{");
		System.out.println(index);
		jsonarray = jsonarray.substring(0,index-1);
		
		jsonarray = octaveEJB.verwerkstringdata(jsonarray);
	
	/*	try {
			json = new JSONObject(jsonarray);
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/

		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		System.out.println(dateFormat.format(date));			//loggen huidige datum
        
		int idmeting;
		Spotter spotter;
		Project project = projectEJB.findProjectById(2); //project nog vinden via doorgestuurde jsonarray
		
		//Meting meting1 = new Meting(2,"titel","2015-12-12 00:00:00",meting,"foto", project);
		String [] resultaten = octaveEJB.createdata(jsonarray);
		
		Meting meting1 = new Meting();
		meting1.setTitel("titel");
		meting1.setTijdstip(dateFormat.format(date));
		meting1.setResultaten(jsonarray);
		meting1.setIdProject(project);
		//meting1.setDataset1(resultaten[0]);
		//meting1.setDataset2(resultaten[1]);
		em.persist(meting1);	
		
	}
	
	public byte[] zoekDataset1(int idMeting){
		Query q = em.createQuery("SELECT m FROM Meting m WHERE m.idMeting= :id");
		q.setParameter("id", idMeting);
		List<Meting> metingen = q.getResultList();
		Meting m=metingen.get(0);
		return m.getDataset1();
	}
	
	public byte[] zoekDataset2(int idMeting){
		Query q = em.createQuery("SELECT m FROM Meting m WHERE m.idMeting= :id");
		q.setParameter("id", idMeting);
		List<Meting> metingen = q.getResultList();
		Meting m=metingen.get(0);
		return m.getDataset2();
	}
	
	public void wissenMeting(int idMeting){
		Meting met = em.find(Meting.class, idMeting);
		em.remove(met);
	}
	
	public byte[] zoekFoto(int idMeting){
		Query q = em.createQuery("SELECT m FROM Meting m WHERE m.idMeting= :id");
		q.setParameter("id", idMeting);
		List<Meting> metingen = q.getResultList();
		Meting m=metingen.get(0);
		return m.getFoto();
		
	}
	
}
