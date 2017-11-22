package Vibrationspotter;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import model.Meting;

public class MetingManagementEJB implements MetingManagementEJBLocal{
	
	@PersistenceContext(unitName="vibrationspotter")
	private EntityManager em;

	@EJB
	private PersonManagementEJBLocal personEJB;

	@Resource
	private SessionContext ctx;
	
	@Override
	public Meting findMeting(String titelMeting) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addMeting(String gebruikersnaam, String titel, String Locatie, int x, int y, int z) {
		// TODO Auto-generated method stub
		
	}

}
