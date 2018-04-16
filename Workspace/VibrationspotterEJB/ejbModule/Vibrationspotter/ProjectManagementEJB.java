package Vibrationspotter;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.Temporal;
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

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import model.Project;

@Named
@Stateless
public class ProjectManagementEJB implements ProjectManagementEJBLocal {

	@PersistenceContext(unitName = "demodb")
	private EntityManager em;

	@Resource
	private SessionContext ctx;

	@EJB
	private MetingManagementEJBLocal metingejb;

	@Override
	public Project findProject(String titel) {
		Query q = em.createQuery("SELECT p FROM Project  WHERE p.titel = : titel");
		q.setParameter(1, titel);
		List<Project> p = q.getResultList();
		if (p.size() != 1)
			System.out.println("Meerdere dezelfde titels!");
		return p.get(0);
	}

	@Override
	public Project findProjectById(int id) {
		Query q = em.createQuery("SELECT p FROM Project p WHERE p.idProject = :id");
		q.setParameter("id", id);
		List<Project> projecten = q.getResultList();
		return projecten.get(0);
	}

	@Override
	public void addProject(Project project) {
		em.persist(project);

	}

	@Override
	public void RemoveProject(Project project) {
		em.remove(project);

	}

	public void wissenProject(int idProject) {
		// eerst metingen van project wissen, vooraleer project wissen
		metingejb.wissenMetingenByProjectid(idProject);
		Project pro = em.find(Project.class, idProject);
		em.remove(pro);

	}

	public List<Project> findAllProjecten() {
		Query q = em.createQuery("SELECT p FROM Project p ORDER BY p.idProject ASC");
		List<Project> projecten = q.getResultList();
		return projecten;
	}

	/*
	 * public List<Project> findProjectenBijPesoon(int id){ Query q = em.
	 * createQuery("SELECT p FROM Project p WHERE idPersoon = id AND  = ORDER BY p.idProject ASC"
	 * ); return null; }
	 */

	/*
	 * public List<Project> findProjectById(int idProject) { Query q =
	 * em.createQuery("SELECT p FROM Project p WHERE p.idProject = :idProject");
	 * q.setParameter("idProject", idProject); List<Project> projecten =
	 * q.getResultList(); return projecten; }
	 */

	/*
	 * public List<Project> findAllUserProjecten() { Query q =
	 * em.createQuery("SELECT p FROM Project p WHERE ORDER BY p.idProject ASC");
	 * List<Project> projecten = q.getResultList(); return projecten; }
	 */

	@Override
	public boolean checkQR(String ingegevenstring) throws IOException {
		
		String qrString = null;
		
		ingegevenstring = ingegevenstring.substring(1, ingegevenstring.length() - 1);
		JSONObject json = null;
		
		try {
			json = new JSONObject(ingegevenstring);
			qrString=json.getString("QR_code");				//qrString = waarde van meegegeven JSONArray
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

	@Override
	public void getQRCode(int id) {
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

	public String FindAllProjectsForApp(String ingegevenstring) {
		ingegevenstring = ingegevenstring.substring(1, ingegevenstring.length() - 1);
		JSONObject json = null;
		JSONArray jArray = new JSONArray();

		try {
			json = new JSONObject(ingegevenstring);
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			int idPersoon = (int) json.get("idPersoon");

			Query q = em.createQuery("SELECT p FROM Project p ORDER BY p.idProject ASC");
			List<Project> projecten = q.getResultList();

			for (int i = 0; i < projecten.size(); i++) {
				JSONObject json2 = new JSONObject();
				json2.put("projectid", projecten.get(i).getIdProject());
				json2.put("projecttitel", projecten.get(i).getTitel());
				json2.put("projectbeschrijving", projecten.get(i).getBeschrijving());
				jArray.put(json2);
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return jArray.toString();

	}

}
