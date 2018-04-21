package Vibrationspotter;

import java.io.IOException;


public interface QrManagementEJBLocal {
	
	
	public byte[] getQRCodeImage(String text, int width, int height);

}
