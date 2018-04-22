package Vibrationspotter;

import java.io.InputStream;
import java.io.OutputStream;

public class SyncPipe implements Runnable {
	/*
	 * klasse die helpt met io in octave, niet zelfgeschreven klasse.
	 */
	private final OutputStream ostrm_;
	private final InputStream istrm_;
	byte[] buffer = new byte[1024];
	public SyncPipe(InputStream istrm, OutputStream ostrm) {
		istrm_ = istrm;
		ostrm_ = ostrm;
		buffer = new byte[1024];
	}

	public void run() {
		try {
		//	final byte[] buffer = new byte[1024];
			for (int length = 0; (length = istrm_.read(buffer)) != -1;) {
				ostrm_.write(buffer, 0, length);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public InputStream getInputStream(){
		return istrm_;
	}

	public OutputStream getOutputStream(){
		return ostrm_;
	}
	
	public byte[] getBuffer(){
		return buffer;
	}
	

}