package Vibrationspotter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

@Named
@Stateless
public class QrManagementEJB implements QrManagementEJBLocal {
	
	@PersistenceContext(unitName="demodb")
	private EntityManager em;
	
	@EJB
	private ProjectManagementEJBLocal projectEJB;

	@Resource
	private SessionContext ctx;
	
	
	public byte[] getQRCodeImage(String text, int width, int height) {
	    QRCodeWriter qrCodeWriter = new QRCodeWriter();
	    BitMatrix bitMatrix = null;
		try {
			bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);
		} catch (WriterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
	    try {
			MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    byte[] pngData = pngOutputStream.toByteArray(); 
	    return pngData;
	}

}
