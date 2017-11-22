package Vibrationspotter;

import model.Persoon;

public interface PersonManagementEJBLocal {
		public Persoon findPersoon(String gebruikersnaam);
		
		void addPersoon(Persoon persoon);
	}


