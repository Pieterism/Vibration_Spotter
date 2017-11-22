package Vibrationspotter;

import javax.ejb.Local;

import model.Meting;

@Local
public interface MetingManagementEJBLocal {
	public Meting findMeting(String titelMeting);
	
	void addMeting(Meting meting);
}
