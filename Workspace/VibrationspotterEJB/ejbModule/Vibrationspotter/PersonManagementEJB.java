package Vibrationspotter;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.persistence.Query;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import model.Meting;
import model.Persoon;

public class PersonManagementEJB implements PersonManagementEJBLocal {
	
	@PersistenceContext(unitName="vibrationspotter")
	private EntityManager em;
	
	@Resource
	private SessionContext ctx;
	

	@Override
	public Persoon findPersoon(String gebruikersnaam) {
		Query q = em.createQuery("SELECT p FROM Persoon  WHERE p.voornaam = : voornaam" );
		q.setParameter(1, gebruikersnaam);
		return null;
	}

	@Override
	public void addPersoon(String gebruikersnaam, String password) {
		Query q = em.createQuery("INSERT INTO Persoon (p.voornaam,p.achternaam) VALUES (: voornaam,: achternaam)" );
		q.setParameter(1, gebruikersnaam);
		q.setParameter(2, password);	//password zit momenteel in achternaam
		
	}
}
