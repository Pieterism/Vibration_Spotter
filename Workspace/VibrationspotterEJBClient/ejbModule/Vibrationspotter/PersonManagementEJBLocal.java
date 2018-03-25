package Vibrationspotter;

import java.util.List;

import javax.ejb.Local;

import model.Persoon;

@Local
public interface PersonManagementEJBLocal {
	// public Persoon findPersoon(String gebruikersnaam); //mag verwijderd
	// worden


	Persoon findPersoon(String voornaam, String achternaam);

	void addPersoon(Persoon p);
	
	public Persoon findPersoonByEmail(String emailadres);
	
	public Persoon findPersoonByid(int id);
	
	public List<Persoon> findAllPersons();
		

	}
