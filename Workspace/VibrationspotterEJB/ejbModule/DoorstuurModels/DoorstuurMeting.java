package DoorstuurModels;

import org.w3c.dom.Element;

import com.sun.org.apache.xml.internal.security.utils.Base64;

import model.Meting;

public class DoorstuurMeting {

	private int idMeting;
	
	private String titel;

	private String tijdstip;

	private byte[] foto;

	private String opmerking;

	private String dataset1;

	private String dataset2;

	public DoorstuurMeting(){}
	
	public DoorstuurMeting(Meting m){
		this.idMeting = m.getIdMeting();
		this.titel = m.getTitel();
		this.tijdstip = m.getTijdstip();
		this.foto = m.getFoto();
		this.opmerking = m.getOpmerking();
		this.dataset1 = Base64.encode(m.getDataset1());
		this.dataset2 = Base64.encode(m.getDataset2());
	}

	public String getDataSet1() {
		return dataset1;
	}
}
