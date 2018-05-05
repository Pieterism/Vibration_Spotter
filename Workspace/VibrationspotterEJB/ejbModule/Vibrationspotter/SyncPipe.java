package Vibrationspotter;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Class needed for Octave file processing
 * 
 * @author Birgen Vermang, Thomas Bruneel, Pieter-Jan Vanhaverbeke, Pieter
 *         Vanderhaegen
 *
 */
public class SyncPipe implements Runnable {

	private final OutputStream ostrm_;

	private final InputStream istrm_;

	byte[] buffer = new byte[1024];

	/**
	 * constructor buffer initialized
	 * 
	 * @param istrm
	 *            inputstream
	 * @param ostrm
	 *            outputstream
	 * 
	 */
	public SyncPipe(InputStream istrm, OutputStream ostrm) {
		istrm_ = istrm;
		ostrm_ = ostrm;
		buffer = new byte[1024];
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		try {
			// final byte[] buffer = new byte[1024];
			for (int length = 0; (length = istrm_.read(buffer)) != -1;) {
				ostrm_.write(buffer, 0, length);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return inputstream
	 */
	public InputStream getInputStream() {
		return istrm_;
	}

	/**
	 * @return outputstream
	 */
	public OutputStream getOutputStream() {
		return ostrm_;
	}

	/**
	 * @return buffer
	 */
	public byte[] getBuffer() {
		return buffer;
	}

}