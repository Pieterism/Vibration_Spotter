package DoorstuurModels;

import org.w3c.dom.Element;

import com.sun.org.apache.xml.internal.security.utils.Base64;

import model.Meting;

public class DoorstuurMeting {

	private int idMeting;
	
	private String titel;

	private String tijdstip;

	private String foto;

	private String opmerking;

	private String dataset1;

	private String dataset2;

	public DoorstuurMeting(){}
	
	public DoorstuurMeting(Meting m){
		this.idMeting = m.getIdMeting();
		this.titel = m.getTitel();
		this.tijdstip = m.getTijdstip();
		this.foto = Base64.encode(m.getFoto());
		this.opmerking = m.getOpmerking();
		this.dataset1 = Base64.encode(m.getDataset1());
		this.dataset2 = Base64.encode(m.getDataset2());
	}
	

	public DoorstuurMeting(int idMeting, String titel, String tijdstip, String foto, String opmerking, String dataset1,
			String dataset2) {
		super();
		this.idMeting = idMeting;
		this.titel = titel;
		this.tijdstip = tijdstip;
		this.foto = foto;
		this.opmerking = opmerking;
		this.dataset1 = dataset1;
		this.dataset2 = dataset2;
	}

	public String getDataSet1() {
		return dataset1;
	}
}
