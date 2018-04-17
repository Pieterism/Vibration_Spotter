package Vibrationspotter;

import javax.ejb.Local;

import model.Meting;
import model.Project;
import model.Spotter;

import java.util.List;

//@Local
public interface MetingManagementEJBLocal {
	public Meting findMeting(String titelMeting);

	void addMeting(Meting meting);
	
	public List<Meting> findMetingenByIdProject(int idProject);

	public void wissenMetingenByProjectid(int idProject);

	public void ToevoegenMetingResultaten(String JSONarray);
	
	public String zoekDataset1(int idMeting);
	
	public String zoekDataset2(int idMeting);

	public void wissenMeting(int idMeting);
}
