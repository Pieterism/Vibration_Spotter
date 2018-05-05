package DoorstuurModels;

import com.sun.org.apache.xml.internal.security.utils.Base64;

import model.Meting;

/**
 * Simple version of Meting class. Used to simplify working with Gson
 * 
 * @author Birgen Vermang, Thomas Bruneel, Pieter-Jan Vanhaverbeke, Pieter
 *         Vanderhaegen
 *
 */
public class DoorstuurMeting {

	private String titel;
	private String tijdstip;
	private String foto;
	private String opmerking;
	private String dataset1;
	private String dataset2;

	/**
	 * Default Constructor
	 */
	public DoorstuurMeting() {
	}

	/**
	 * Constructor
	 * 
	 * @param m
	 *            Meting to send
	 */
	public DoorstuurMeting(Meting m) {
		m.getIdMeting();
		this.titel = m.getTitel();
		this.tijdstip = m.getTijdstip();
		this.foto = Base64.encode(m.getFoto());
		this.opmerking = m.getOpmerking();
		this.dataset1 = Base64.encode(m.getDataset1());
		this.dataset2 = Base64.encode(m.getDataset2());
	}

	/**
	 * Constructor
	 * 
	 * @param idMeting
	 *            id meting
	 * @param titel
	 *            meting title
	 * @param tijdstip
	 *            time of meting
	 * @param foto
	 *            picture of meting
	 * @param opmerking
	 *            comment on meting
	 * @param dataset1
	 *            acceleration in function of time data
	 * @param dataset2
	 *            amplitude in function of frequency data
	 */
	public DoorstuurMeting(int idMeting, String titel, String tijdstip, String foto, String opmerking, String dataset1,
			String dataset2) {
		super();
		this.titel = titel;
		this.tijdstip = tijdstip;
		this.foto = foto;
		this.opmerking = opmerking;
		this.dataset1 = dataset1;
		this.dataset2 = dataset2;
	}

	/**
	 * Getter for acceleration in function of time data
	 * 
	 * @return
	 */
	public String getDataSet1() {
		return dataset1;
	}
}
