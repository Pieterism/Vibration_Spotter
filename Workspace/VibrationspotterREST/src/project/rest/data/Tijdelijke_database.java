package project.rest.data;

import java.util.List;
import java.util.ArrayList;

public class Tijdelijke_database {
	private static Tijdelijke_database tdb = null;
	
	private List<Meting> metingen;
	
	private Tijdelijke_database(){
		metingen = new ArrayList<Meting>();
	}
	
	public static Tijdelijke_database getInstance(){
		if(tdb == null) tdb = new Tijdelijke_database();
		return tdb;
	}
	
	public void createMeting(Meting meting){
		meting.setId(""+ metingen.size());
		metingen.add(meting);
	}
	
	public void merge(Meting meting){
		for(Meting metingIt : metingen){
			if(metingIt.getId().equals(meting.getId())){
				metingIt.setTijd(meting.getTijd());
				metingIt.setX(meting.getX());
				metingIt.setY(meting.getY());
				metingIt.setZ(meting.getZ());
			}
		}
	}
	
	public void remove(Meting meting){
		for(Meting metingIt : metingen) if(metingIt.getId().equals(meting.getId())) metingen.remove(metingIt);
	}

	public List<Meting> getAllMetingen(){
		return metingen;
	}
	
	public Meting find(String id){
		Meting meting = null;
		for(Meting metingIt : metingen) if(metingIt.getId().equals(meting.getId())) meting = metingIt;
		return meting;
	}
}
