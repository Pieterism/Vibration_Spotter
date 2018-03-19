package model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

@Entity
@Table(name = "Project")
@NamedQuery(name = "Project.findAll", query = "SELECT p FROM Project p")
public class Project implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idProject", nullable = false)
	private int idProject;

	@Column(name = "titel", nullable = false)
	private String titel;

	@Column(name = "type", nullable = false)
	private String type; // aan elk type meting een int toekennen en adhv deze int
						// bepalen welk type het is.

	@Column(name = "latitude", nullable = false)
	private float latitude;

	@Column(name = "longtitude", nullable = false)
	private float longtitude;

	@Column(name = "goedgekeurd", nullable = false)
	private boolean goedgekeurd;

	@Column(name = "QR", nullable = false)
	private String QR;

	@Lob
	@Column(name = "beschrijving", length = 512)
	private String beschrijving;

	@ManyToMany(targetEntity = Persoon.class)
	private Set personen;

	public Project() {
		//super();
		this.latitude = 0;
		this.longtitude = 0;
		this.goedgekeurd = false;
		QR = UUID.randomUUID().toString();

	}

	public Project(int idProject, String titel, String type, float latitude, float longtitude, boolean goedgekeurd,
			String qR, String beschrijving, Set personen) {
		super();
		this.idProject = idProject;
		this.titel = titel;
		this.type = type;
		this.latitude = latitude;
		this.longtitude = longtitude;
		this.goedgekeurd = goedgekeurd;
		QR = qR;
		this.beschrijving = beschrijving;
		this.personen = personen;
	}
	

	public int getIdProject() {
		return idProject;
	}

	public void setIdProject(int idProject) {
		this.idProject = idProject;
	}

	public String getTitel() {
		return titel;
	}

	public void setTitel(String titel) {
		this.titel = titel;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public float getLatitude() {
		return latitude;
	}

	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	public float getLongtitude() {
		return longtitude;
	}

	public void setLongtitude(float longtitude) {
		this.longtitude = longtitude;
	}

	public boolean isGoedgekeurd() {
		return goedgekeurd;
	}

	public void setGoedgekeurd(boolean goedgekeurd) {
		this.goedgekeurd = goedgekeurd;
	}
	
	public void createQR() {

		// FILEPATH ZELF AANPASSEN NAAR WAAR QR GESCHREVEN MOET WORDEN.
		String filePath = "D:\\Google Drive\\School\\2017-2018\\Project\\QR.png";
		int size = 450;
		String fileType = "png";
		File myFile = new File(filePath);

		try {

			Map<EncodeHintType, Object> hintMap = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
			hintMap.put(EncodeHintType.CHARACTER_SET, "UTF-8");

			hintMap.put(EncodeHintType.MARGIN, 1); // default = 4
			hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

			QRCodeWriter qrCodeWriter = new QRCodeWriter();
			BitMatrix byteMatrix = qrCodeWriter.encode(this.QR, BarcodeFormat.QR_CODE, size, size, hintMap);
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

	public String getQR() {
		return QR;
	}

	public void setQR(String qR) {
		QR = qR;
	}

	public String getBeschrijving() {
		return beschrijving;
	}

	public void setBeschrijving(String beschrijving) {
		this.beschrijving = beschrijving;
	}

	public Set getPersonen() {
		return personen;
	}

	public void setPersonen(Set personen) {
		this.personen = personen;
	}

}
