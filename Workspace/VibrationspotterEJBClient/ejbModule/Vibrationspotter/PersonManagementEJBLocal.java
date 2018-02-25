package Vibrationspotter;

import javax.ejb.Local;

import model.Persoon;

@Local
public interface PersonManagementEJBLocal {
	//public Persoon findPersoon(String gebruikersnaam);		//mag verwijderd worden

	public void addPersoon(Persoon persoon);

	Persoon findPersoon(String voornaam, String achternaam);
}


