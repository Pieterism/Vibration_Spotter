package Vibrationspotter;

import model.Persoon;

public interface PersonManagementEJBLocal {
		public Persoon findPersoon(String gebruikersnaam);
		
		public void addPersoon(String gebruikersnaam, String password);
	}


