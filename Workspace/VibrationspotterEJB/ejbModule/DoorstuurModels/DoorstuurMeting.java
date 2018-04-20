package DoorstuurModels;

import model.Meting;

public class DoorstuurMeting {

	private int idMeting;
	
	private String titel;

	private String tijdstip;

	private byte[] foto;

	private String opmerking;

	private byte[] dataset1;

	private byte[] dataset2;

	public DoorstuurMeting(){}
	
	public DoorstuurMeting(Meting m){
		this.idMeting = m.getIdMeting();
		this.titel = m.getTitel();
		this.tijdstip = m.getTijdstip();
		this.foto = m.getFoto();
		this.opmerking = m.getOpmerking();
		this.dataset1 = m.getDataset1();
		this.dataset2 = m.getDataset2();
	}
}
