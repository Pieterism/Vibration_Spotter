package Vibrationspotter;

import javax.ejb.Local;

import model.Meting;
import java.util.List;

@Local
public interface MetingManagementEJBLocal {
	public Meting findMeting(String titelMeting);

	void addMeting(Meting meting);
	
	public List<Meting> findMetingenByIdProject(int idProject);
}
