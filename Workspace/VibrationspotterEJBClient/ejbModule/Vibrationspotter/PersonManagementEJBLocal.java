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
	
	Persoon findPersoonByEmail(String emailadres);

	Persoon findPersoonByid(int id);
	
	Spotter findSpotterByGebruiksnaam(String gebruikersnaam);

	String zoekEmailadres(int idPersoon);
	
	int haalProjectenSizeVanPersoon(int id);
	
	

		

	}
