package Vibrationspotter;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import model.Meting;

public class MetingManagementEJB implements MetingManagementEJBLocal{
	
	@PersistenceContext(unitName="vibrationspotter")
	private EntityManager em;

	@EJB
	private PersonManagementEJBLocal personEJB;

	@Resource
	private SessionContext ctx;
	
	public MetingManagementEJB(){};
	
	@Override
	public Meting findMeting(String titelMeting) {
		Query q = em.createQuery("SELECT id FROM Meting m WHERE m.titel = :titel");
		q.setParameter(1, titelMeting);
		
		return em.find(Meting.class, titelMeting);
	}

	@Override
	public void addMeting(Meting meting) {
		em.persist(meting);
	}

}
