package Vibrationspotter;

import javax.ejb.Local;

import model.Persoon;

@Local
public interface PersonManagementEJBLocal {
		public Persoon findPersoon(String gebruikersnaam);
		
		public void addPersoon(Persoon persoon);
	}


