package Vibrationspotter;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import com.sun.org.apache.xml.internal.security.utils.Base64;

import DoorstuurModels.DoorstuurMeting;
import model.Meting;
import model.Project;
import model.Spotter;

/**
 * Bean to handle Meting class queries and operations on database
 * 
 * @author Birgen Vermang, Thomas Bruneel, Pieter-Jan Vanhaverbeke, Pieter
 *         Vanderhaegen
 *
 */
@Named
@Stateless
public class MetingManagementEJB implements MetingManagementEJBLocal {

	@PersistenceContext(unitName = "demodb")
	private EntityManager em;

	@EJB
	private PersonManagementEJBLocal personEJB;

	@EJB
	private OctaveManagementEJBLocal octaveEJB;

	@EJB
	private ProjectManagementEJBLocal projectEJB;

	@Resource
	private SessionContext ctx;

	public MetingManagementEJB() {
	};

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * Vibrationspotter.MetingManagementEJBLocal#findMeting(java.lang.String)
	 */
	@Override
	public Meting findMeting(String titelMeting) {
		// Methode zoeken van meting met bepaalde titel.
		Query q = em.createQuery("SELECT id FROM Meting m WHERE m.titel = :titel");
		q.setParameter(1, titelMeting);

		return em.find(Meting.class, titelMeting);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Vibrationspotter.MetingManagementEJBLocal#addMeting(model.Meting)
	 */
	@Override
	public void addMeting(Meting meting) {
		em.persist(meting);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * Vibrationspotter.MetingManagementEJBLocal#findMetingenByIdProject(int)
	 */
	public List<Meting> findMetingenByIdProject(int idProject) {
		Query q = em.createQuery("SELECT m FROM Meting m WHERE m.idProject.idProject= :fname");
		q.setParameter("fname", idProject);
		List<Meting> metingen = q.getResultList();
		return metingen;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * Vibrationspotter.MetingManagementEJBLocal#wissenMetingenByProjectid(int)
	 */
	public void wissenMetingenByProjectid(int idProject) {
		Query q = em.createQuery("SELECT m FROM Meting m WHERE m.idProject.idProject= :id");
		q.setParameter("id", idProject);
		List<Meting> metingen = q.getResultList();
		for (Meting m : metingen) {
			Meting met = em.find(Meting.class, m.getIdMeting());
			em.remove(met);

		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * Vibrationspotter.MetingManagementEJBLocal#ToevoegenMetingResultaten2(java
	 * .lang.String, java.lang.String)
	 */
	public void ToevoegenMetingResultaten2(String gegevens, String id) {

		byte[] imageByte = null;
		Gson gson = new Gson();
		Type type = new TypeToken<List<Map<String, String>>>() {
		}.getType();
		List<Map<String, String>> doorstuurmeting = gson.fromJson(gegevens, type);

		// text,descripotion,imagestring,meetstring
		String leesBareData = null;
		try {
			leesBareData = new String(Base64.decode(doorstuurmeting.get(0).get("dataset1")));
			imageByte = Base64.decode((doorstuurmeting.get(0).get("foto")));
		} catch (Base64DecodingException e) {
			e.printStackTrace();
		}
		//System.out.println(leesBareData);

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		//System.out.println(dateFormat.format(date)); // loggen huidige datum

		Project project = projectEJB.findProjectById(Integer.parseInt(id));

		//String[] verwerkteresultaten = octaveEJB.createdata(leesBareData);

		Meting meting1 = new Meting();
		meting1.setTitel(doorstuurmeting.get(0).get("titel"));
		meting1.setTijdstip(dateFormat.format(date));
		meting1.setResultaten("hello");
		meting1.setIdProject(project);
		meting1.setFoto(imageByte);
		meting1.setOpmerking(doorstuurmeting.get(0).get("opmerking"));
		meting1.setDataset1(leesBareData.getBytes());
		meting1.setDataset2(findMetingById(1).getDataset2());

		em.persist(meting1);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * Vibrationspotter.MetingManagementEJBLocal#ToevoegenMetingResultaten(java.
	 * lang.String)
	 */
	public void ToevoegenMetingResultaten(String jsonarray) {
		/*
		 * Methode die Resultaten gekregen van APP verwerkt en dan doorstuurt
		 * naar databank. De verwerking gebeurt in de OctaveManagementEJB
		 * klassen opgeroepen door verwerkstringdata(String data) en
		 * CreateData(String s)
		 */

		jsonarray = jsonarray.substring(1, jsonarray.length() - 1);
		// JSONObject json = null;

		int index = jsonarray.lastIndexOf("{");
		System.out.println(index);
		jsonarray = jsonarray.substring(0, index - 1);

		jsonarray = octaveEJB.verwerkstringdata(jsonarray);

		/*
		 * try { json = new JSONObject(jsonarray); } catch (JSONException e1) {
		 * // TODO Auto-generated catch block e1.printStackTrace(); }
		 */

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		System.out.println(dateFormat.format(date)); // loggen huidige datum

		int idmeting;
		Spotter spotter;
		Project project = projectEJB.findProjectById(2); // project nog vinden
															// via doorgestuurde
															// jsonarray

		// Meting meting1 = new Meting(2,"titel","2015-12-12
		// 00:00:00",meting,"foto", project);
		String[] resultaten = octaveEJB.createdata(jsonarray);

		Meting meting1 = new Meting();
		meting1.setTitel("titel");
		meting1.setTijdstip(dateFormat.format(date));
		meting1.setResultaten(jsonarray);
		meting1.setIdProject(project);
		// meting1.setDataset1(resultaten[0].getBytes());
		// meting1.setDataset2(resultaten[1].getBytes());
		em.persist(meting1);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Vibrationspotter.MetingManagementEJBLocal#zoekDataset1(int)
	 */
	public byte[] zoekDataset1(int idMeting) {
		Query q = em.createQuery("SELECT m FROM Meting m WHERE m.idMeting= :id");
		q.setParameter("id", idMeting);
		List<Meting> metingen = q.getResultList();
		Meting m = metingen.get(0);
		return m.getDataset1();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Vibrationspotter.MetingManagementEJBLocal#zoekDataset2(int)
	 */
	public byte[] zoekDataset2(int idMeting) {
		Query q = em.createQuery("SELECT m FROM Meting m WHERE m.idMeting= :id");
		q.setParameter("id", idMeting);
		List<Meting> metingen = q.getResultList();
		Meting m = metingen.get(0);
		return m.getDataset2();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Vibrationspotter.MetingManagementEJBLocal#wissenMeting(int)
	 */
	public void wissenMeting(int idMeting) {
		Meting met = em.find(Meting.class, idMeting);
		em.remove(met);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Vibrationspotter.MetingManagementEJBLocal#zoekFoto(int)
	 */
	public byte[] zoekFoto(int idMeting) {
		Query q = em.createQuery("SELECT m FROM Meting m WHERE m.idMeting= :id");
		q.setParameter("id", idMeting);
		List<Meting> metingen = q.getResultList();
		Meting m = metingen.get(0);
		return m.getFoto();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * Vibrationspotter.MetingManagementEJBLocal#haalProjectMetingen(java.lang.
	 * String)
	 */
	public String haalProjectMetingen(String id) {
		int projectid = Integer.parseInt(id);
		Query q = em.createQuery("SELECT p FROM Project p WHERE p.idProject= :id");
		q.setParameter("id", projectid);
		List<Project> projecten = q.getResultList();
		Project idProject = projecten.get(0);
		// Waarom doe je dit?
		// Je zoekt op id om dan de id eruit te halen?

		Query q2 = em.createQuery("SELECT m FROM Meting m WHERE m.idProject = :idProject");
		q2.setParameter("idProject", idProject);
		List<Meting> metingen = q2.getResultList();

		List<DoorstuurMeting> doorstuurMetingen = new ArrayList<>();
		for (Meting m : metingen) {
			doorstuurMetingen.add(new DoorstuurMeting(m));
		}

		// List<DoorstuurProject> doorstuurProjecten = new ArrayList<>();
		// for(Project p: projecten) doorstuurProjecten.add(new
		// DoorstuurProject(p));

		Gson gson = new Gson();
		String metingenJson = gson.toJson(doorstuurMetingen);

		return metingenJson;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Vibrationspotter.MetingManagementEJBLocal#findMetingById(int)
	 */
	public Meting findMetingById(int id) {
		Query q = em.createQuery("SELECT m FROM Meting m WHERE m.idMeting = :id");
		q.setParameter("id", id);
		List<Meting> metingen = q.getResultList();
		return metingen.get(0);

	}

}
