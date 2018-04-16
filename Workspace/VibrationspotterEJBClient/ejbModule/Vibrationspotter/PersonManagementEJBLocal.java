package Vibrationspotter;

import javax.ejb.Local;

import model.Persoon;
import model.Spotter;

//@Local
public interface PersonManagementEJBLocal {
	// public Persoon findPersoon(String gebruikersnaam); //mag verwijderd
	// worden


	Persoon findPersoon(String voornaam, String achternaam);

	void addPersoon(Persoon p);
	
	public Persoon findPersoonByEmail(String emailadres);

	Persoon findPersoonByid(int id);
	
	public Spotter findSpotterByGebruiksnaam(String gebruikersnaam);
		

	}
