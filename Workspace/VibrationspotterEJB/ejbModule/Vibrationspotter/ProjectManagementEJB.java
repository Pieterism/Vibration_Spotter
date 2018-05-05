package Vibrationspotter;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.imageio.ImageIO;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import DoorstuurModels.DoorstuurProject;
import model.Persoon;
import model.Project;

/**
 * Bean to handle Project class queries and operations on database
 * 
 * @author Birgen Vermang, Thomas Bruneel, Pieter-Jan Vanhaverbeke, Pieter
 *         Vanderhaegen
 *
 */
@Named
@Stateless
public class ProjectManagementEJB implements ProjectManagementEJBLocal {

	@PersistenceContext(unitName = "demodb")
	private EntityManager em;

	@Resource
	private SessionContext ctx;

	@EJB
	private MetingManagementEJBLocal metingejb;

	@EJB
	private PersonManagementEJBLocal persoonejb;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * Vibrationspotter.ProjectManagementEJBLocal#findProject(java.lang.String)
	 */
	@Override
	public Project findProject(String titel) {
		Query q = em.createQuery("SELECT p FROM Project  WHERE p.titel = : titel");
		q.setParameter(1, titel);
		List<Project> p = q.getResultList();
		if (p.size() != 1)
			System.out.println("Meerdere dezelfde titels!");
		return p.get(0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * Vibrationspotter.ProjectManagementEJBLocal#findPersonByIdProject(int)
	 */
	@Override
	public String findPersonByIdProject(int idProject) {
		Query q = em.createQuery("SELECT p FROM Project p WHERE p.idProject = :id");
		q.setParameter("id", idProject);
		List<Project> projecten = q.getResultList();
		Persoon p = projecten.get(0).getIdPersoon();

		JSONObject jObj = new JSONObject();
		JSONArray jArray = new JSONArray();

		try {
			jObj.put("gebruikersnaam", p.getEmailadres());
			jArray.put(jObj);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jArray.toString();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Vibrationspotter.ProjectManagementEJBLocal#findProjectById(int)
	 */
	@Override
	public Project findProjectById(int id) {
		Query q = em.createQuery("SELECT p FROM Project p WHERE p.idProject = :id");
		q.setParameter("id", id);
		List<Project> projecten = q.getResultList();
		return projecten.get(0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * Vibrationspotter.ProjectManagementEJBLocal#findProjectByEmail(java.lang.
	 * String)
	 */
	@Override
	public Project findProjectByEmail(String email) {
		Query q = em.createQuery("SELECT p FROM Project p WHERE p.idProject = :email");
		q.setParameter("email", email);
		List<Project> projecten = q.getResultList();
		return projecten.get(0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Vibrationspotter.ProjectManagementEJBLocal#addProject(model.Project)
	 */
	@Override
	public void addProject(Project project) {
		em.persist(project);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * Vibrationspotter.ProjectManagementEJBLocal#RemoveProject(model.Project)
	 */
	@Override
	public void RemoveProject(Project project) {
		em.remove(project);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Vibrationspotter.ProjectManagementEJBLocal#wissenProject(int)
	 */
	@Override
	public void wissenProject(int idProject) {
		// eerst metingen van project wissen, vooraleer project wissen
		metingejb.wissenMetingenByProjectid(idProject);
		Project pro = em.find(Project.class, idProject);
		em.remove(pro);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Vibrationspotter.ProjectManagementEJBLocal#findAllProjecten()
	 */
	@Override
	public List<Project> findAllProjecten() {
		Query q = em.createQuery("SELECT p FROM Project p ORDER BY p.idProject ASC");
		List<Project> projecten = q.getResultList();
		return projecten;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Vibrationspotter.ProjectManagementEJBLocal#findMijnProjecten(int)
	 */
	@Override
	public List<Project> findMijnProjecten(int idPersoon) {
		Persoon p = persoonejb.findPersoonByid(idPersoon);
		Query q = em.createQuery("SELECT p FROM Project p WHERE p.idPersoon = :idPersoon");
		q.setParameter("idPersoon", p);
		List<Project> projecten = q.getResultList();
		return projecten;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Vibrationspotter.ProjectManagementEJBLocal#checkQR(java.lang.String)
	 */
	@Override
	public boolean checkQR(String ingegevenstring) throws IOException {

		String qrString = null;

		ingegevenstring = ingegevenstring.substring(1, ingegevenstring.length() - 1);
		JSONObject json = null;

		try {
			json = new JSONObject(ingegevenstring);
			qrString = json.getString("QR_code"); // qrString = waarde van
													// meegegeven JSONArray
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// Get qr string value from db
		Query q = em.createQuery("SELECT p FROM Project  WHERE p.QR = : qr");
		q.setParameter(1, qrString);
		List<Project> p = q.getResultList();
		if (p.size() != 1)
			return false;
		else {
			// compare timestamps
			Duration duration = Duration.between(LocalDateTime.now(), (Temporal) p.get(0).getTimestamp());
			long diff = Math.abs(duration.toMinutes());
			if (diff < 10) {
				return false;
			}
			return true;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Vibrationspotter.ProjectManagementEJBLocal#getQRCode(int)
	 */
	@Override
	public void getQRCode(int id) {
		/*
		 * Methode om een QR code te genereren. Zal het als een png opslaan op
		 * google Drive.
		 */
		String uniqueID = null;

		Query q = em.createQuery("SELECT p FROM Project  WHERE p.idProject = : id");
		q.setParameter(1, id);
		List<Project> p = q.getResultList();
		if (p.size() != 1)
			System.out.println("Foutieve id");
		else
			p.get(0).setTimestamp(Calendar.getInstance().getTime());
		uniqueID = p.get(0).getQR();

		String filePath = "D:\\Google Drive\\School\\2017-2018\\Project\\Test.png";
		int size = 450;
		String fileType = "png";
		File myFile = new File(filePath);

		try {

			Map<EncodeHintType, Object> hintMap = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
			hintMap.put(EncodeHintType.CHARACTER_SET, "UTF-8");

			// Now with zxing version 3.2.1 you could change border size (white
			// border size to just 1)
			hintMap.put(EncodeHintType.MARGIN, 1); /* default = 4 */
			hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

			QRCodeWriter qrCodeWriter = new QRCodeWriter();
			BitMatrix byteMatrix = qrCodeWriter.encode(uniqueID, BarcodeFormat.QR_CODE, size, size, hintMap);
			int CrunchifyWidth = byteMatrix.getWidth();
			BufferedImage image = new BufferedImage(CrunchifyWidth, CrunchifyWidth, BufferedImage.TYPE_INT_RGB);
			image.createGraphics();

			Graphics2D graphics = (Graphics2D) image.getGraphics();
			graphics.setColor(Color.WHITE);
			graphics.fillRect(0, 0, CrunchifyWidth, CrunchifyWidth);
			graphics.setColor(Color.BLACK);

			for (int i = 0; i < CrunchifyWidth; i++) {
				for (int j = 0; j < CrunchifyWidth; j++) {
					if (byteMatrix.get(i, j)) {
						graphics.fillRect(i, j, 1, 1);
					}
				}
			}
			ImageIO.write(image, fileType, myFile);
		} catch (WriterException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("\n\nYou have successfully created QR Code.");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * Vibrationspotter.ProjectManagementEJBLocal#FindAllProjectsForApp(java.
	 * lang.String)
	 */
	@Override
	public String FindAllProjectsForApp(String ingegevenstring) {

		Gson gson = new Gson();
		Type type = new TypeToken<List<Map<String, String>>>() {
		}.getType();

		List<Map<String, String>> inloggegevens = gson.fromJson(ingegevenstring, type);

		Persoon persoon = persoonejb.findPersoonByEmail(inloggegevens.get(0).get("email"));
		int idPersoon = persoon.getIdPersoon();

		Query q = em.createQuery("SELECT p FROM Project p Where p.idPersoon = :idPersoon", Persoon.class);
		q.setParameter("idPersoon", persoon);
		List<Project> projecten = q.getResultList();

		List<DoorstuurProject> doorstuurProjecten = new ArrayList<>();
		for (Project p : projecten)
			doorstuurProjecten.add(new DoorstuurProject(p));

		String projectenJson = gson.toJson(doorstuurProjecten);

		return projectenJson;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Vibrationspotter.ProjectManagementEJBLocal#update(model.Project)
	 */
	@Override
	public void update(Project p) {
		em.merge(p);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Vibrationspotter.ProjectManagementEJBLocal#checkGoedgekeurd(int)
	 */
	@Override
	public boolean checkGoedgekeurd(int idProject) {
		Query q = em.createQuery("SELECT p FROM Project p WHERE p.idProject = :id");
		q.setParameter("id", idProject);
		List<Project> projecten = q.getResultList();
		if (projecten.size() != 1) {
			return false;
		}
		return projecten.get(0).isGoedgekeurd();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * Vibrationspotter.ProjectManagementEJBLocal#findGoedgekeurdeProjecten()
	 */
	@Override
	public List<Project> findGoedgekeurdeProjecten() {
		Query q = em.createQuery("SELECT p FROM Project p ORDER BY p.idProject ASC");
		List<Project> projecten = q.getResultList();
		List<Project> goedeprojecten = new ArrayList<Project>();
		for (Project p : projecten) {
			if (p.isGoedgekeurd() == true) {
				goedeprojecten.add(p);
			}

		}
		return goedeprojecten;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * Vibrationspotter.ProjectManagementEJBLocal#findProjectByQR(java.lang.
	 * String)
	 */
	@Override
	public Project findProjectByQR(String QR) {
		Query q = em.createQuery("SELECT p FROM Project p WHERE p.QR = :QR");
		q.setParameter("QR", QR);
		List<Project> projecten = q.getResultList();
		return projecten.get(0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * Vibrationspotter.ProjectManagementEJBLocal#ToevoegenProjectenAPP(java.
	 * lang.String)
	 */
	@Override
	public void ToevoegenProjectenAPP(String gegevens) {
		gegevens = gegevens.substring(1, gegevens.length() - 1);
		JSONObject json = null;

		String titel = null;
		String type = "Vibrationspotter";
		String latitude = null;
		String longtitude = null;
		String QR = null;
		String beschrijving = null;
		// String idPersoon = null;
		String email = null;

		try {
			json = new JSONObject(gegevens);
			titel = json.getString("titel");
			latitude = json.getString("latitude");
			longtitude = json.getString("longtitude");
			beschrijving = json.getString("beschrijving");
			email = json.getString("email");

		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Persoon persoon = persoonejb.findPersoonByEmail(email);

		// Persoon persoon =
		// persoonejb.findPersoonByid(Integer.parseInt(idPersoon));

		Project project = new Project();
		project.setTitel(titel);
		project.setBeschrijving(beschrijving);
		project.setLatitude((float) Double.parseDouble(latitude));
		project.setLongtitude((float) Double.parseDouble(longtitude));
		project.setType(type);
		project.setIdPersoon(persoon);

		em.persist(project);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * Vibrationspotter.ProjectManagementEJBLocal#verwijderProjectViaApp(java.
	 * lang.String)
	 */
	@Override
	public void verwijderProjectViaApp(String gegevens) {
		gegevens = gegevens.substring(1, gegevens.length() - 1);
		JSONObject json = null;

		int idProject = 0;
		try {
			json = new JSONObject(gegevens);
			idProject = Integer.parseInt(json.getString("idProject"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Project project = findProjectById(idProject);
		em.remove(project);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * Vibrationspotter.ProjectManagementEJBLocal#haalMetingenSizeProject(int)
	 */
	@Override
	public int haalMetingenSizeProject(int id) {
		Query q = em.createQuery("SELECT p FROM Project p WHERE p.idMeting = :id");
		q.setParameter("id", id);
		int size = q.getResultList().size();
		return size;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * Vibrationspotter.ProjectManagementEJBLocal#HaalprojectviaApp(java.lang.
	 * String)
	 */
	@Override
	public String HaalprojectviaApp(String gegevens) {
		gegevens = gegevens.substring(1, gegevens.length() - 1);
		JSONObject json = null;
		String QR = null;

		Gson gson = new Gson();
		Type type = new TypeToken<List<Map<String, String>>>() {
		}.getType();

		try {
			json = new JSONObject(gegevens);
			QR = json.getString("QR");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Project p = findProjectByQR(QR);
		// System.out.println(p.getTimestamp());
		// DoorstuurProject doorstuurp = new DoorstuurProject(p);

		List<DoorstuurProject> doorstuurProjecten = new ArrayList<>();
		doorstuurProjecten.add(new DoorstuurProject(p));

		String projectenJson = gson.toJson(doorstuurProjecten);

		return projectenJson;

	}

}
