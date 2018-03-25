package Vibrationspotter;

import java.util.List;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.Query;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import model.Persoon;
import model.Project;


@Stateless
public class PersonManagementEJB implements PersonManagementEJBLocal {

	@PersistenceContext(unitName = "demodb") // Aanpassen met xmlfile?
	private EntityManager em;

	@Resource
	private SessionContext ctx;
	
    @Override
	public Persoon findPersoonByid(int id){
		Query q = em.createQuery("SELECT p FROM Persoon p WHERE p.idPersoon = :id");
		q.setParameter("id", id);
		List<Persoon> personen = q.getResultList();
		return personen.get(0);
	
	}
    
	public List<Persoon> findAllPersons() {
		Query q = em.createQuery("SELECT p FROM Persoon p ORDER BY p.idPersoon ASC");
		List<Persoon> personen = q.getResultList();
		return personen;
	}
    

	/*
	 * @Override public Persoon findPersoon(String gebruikersnaam) { Query q =
	 * em.createQuery("SELECT p FROM Persoon  WHERE p.voornaam = : voornaam" );
	 * q.setParameter(1, gebruikersnaam); return null; }
	 */

	@Override
	public Persoon findPersoon(String voornaam, String achternaam) {
		Query q = em.createQuery(
				"SELECT p FROM Persoon  WHERE p.voornaam = : voornaam " + "AND p.achternaam =: achternaam");
		q.setParameter(1, voornaam);
		q.setParameter(2, achternaam);
		return null;
	}

	@Override
	public void addPersoon(Persoon p) {
		em.persist(p);
		
	}
	public Persoon findPersoonByEmail(String emailadres){
			Query q = em.createQuery("SELECT p FROM Persoon p WHERE p.emailadres = :emailadres");
			q.setParameter("emailadres", emailadres);
			List<Persoon> personen = q.getResultList();
			if(personen.size()!=1){
				return null;
			}
			else{
			Persoon p=personen.get(0);
			return p;
			}
		}
	

	
/*	@Override
	public Persoon findPersoonByid(String id){
		Persoon p = (Persoon) em.createNativeQuery("SELECT p FROM Persoon p WHERE p.idPersoon = " + id).getSingleResult();
		return p;
	}*/
	
	
    /*
    @Override
    public User findByEmail(String email) {
        Object obj = em.createNativeQuery("SELECT * FROM User WHERE EMAIL = " + email).getSingleResult();
        User user = (User)obj;
        return user;        
    }*/
	

	
		
	}


