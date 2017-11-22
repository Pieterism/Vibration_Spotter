package Vibrationspotter;

import javax.ejb.Local;

import model.Meting;

@Local
public interface MetingManagementEJBLocal {
	public Meting findMeting(String titelMeting);
	
	public void addMeting(String gebruikersnaam, String titel, String Locatie, int x, int y, int z);
}
