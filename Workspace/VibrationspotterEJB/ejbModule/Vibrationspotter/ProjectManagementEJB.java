package Vibrationspotter;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.imageio.ImageIO;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import model.Persoon;
import model.Project;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

@Named
@Stateless
public class ProjectManagementEJB implements ProjectManagementEJBLocal {

	@PersistenceContext(unitName = "demodb")
	private EntityManager em;

	@Resource
	private SessionContext ctx;

	@Override
	public Project findProject(String titel) {
		Query q = em.createQuery("SELECT p FROM Project  WHERE p.titel = : titel");
		q.setParameter(1, titel);
		return null;
	}

	@Override
	public void createQR(Project project) {
		int id = project.getIdProject();
		Query q = em.createQuery("SELECT p FROM Project WHERE p.idProject = :idProject ");
		q.setParameter(1, id);
		List<Project> projecten = q.getResultList();
		String qr = projecten.get(0).getQR();

		//FILEPATH ZELF AANPASSEN NAAR WAAR QR GESCHREVEN MOET WORDEN.
		String filePath = "D:\\Google Drive\\School\\2017-2018\\Project\\QR.png";
		int size = 250;
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
			BitMatrix byteMatrix = qrCodeWriter.encode(qr, BarcodeFormat.QR_CODE, size, size, hintMap);
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

	@Override
	public void addProject(Project project) {
		em.persist(project);

	}

	@Override
	public void RemoveProject(Project project) {
		em.remove(project);

	}

	public void wissenProject(int idProject) {
		/*
		 * Query q = em.
		 * createQuery("SELECT p FROM Project p WHERE p.idProject = :idProject"
		 * ); q.setParameter("idProject", idProject); List<Project> projecten =
		 * q.getResultList();
		 * System.out.println(projecten.get(0).getIdProject());
		 */

		Project pro = em.find(Project.class, idProject);
		em.remove(pro);
		System.out.println(pro.getIdProject());

	}

	public List<Project> findAllProjecten() {
		Query q = em.createQuery("SELECT p FROM Project p ORDER BY p.idProject ASC");
		List<Project> projecten = q.getResultList();
		return projecten;
	}

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
}
