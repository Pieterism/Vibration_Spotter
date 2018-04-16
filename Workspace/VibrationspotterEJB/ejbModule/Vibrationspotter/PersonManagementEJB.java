package Vibrationspotter;

import java.util.List;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.Query;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import model.Persoon;
import model.Spotter;

@Stateless
public class PersonManagementEJB implements PersonManagementEJBLocal {

	@PersistenceContext(unitName = "demodb") // Aanpassen met xmlfile?
	private EntityManager em;

	@Resource
	private SessionContext ctx;

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
	public Persoon findPersoonByid(int id) {
		Query q = em.createQuery("SELECT p FROM Persoon p WHERE p.idPersoon = :id");
		q.setParameter("id", id);
		List<Persoon> personen = q.getResultList();
		return personen.get(0);
	}

	@Override
	public void addPersoon(Persoon p) {
		em.persist(p);

	}

	public Persoon findPersoonByEmail(String emailadres) {
		Query q = em.createQuery("SELECT p FROM Persoon p WHERE p.emailadres = :emailadres");
		q.setParameter("emailadres", emailadres);
		List<Persoon> personen = q.getResultList();
		if (personen.size() != 1) {
			return null;
		} else {
			Persoon p = personen.get(0);
			return p;
		}
	}

	public Spotter findSpotterByGebruiksnaam(String gebruikersnaam) {
		Query q = em.createQuery("SELECT s FROM Spotter s WHERE s.gebruikersnaam = :gebruikersnaam");
		q.setParameter("gebruikersnaam", gebruikersnaam);
		List<Spotter> spotters = q.getResultList();
		if (spotters.size() != 1) {
			return null;
		} else {
			Spotter s = spotters.get(0);
			return s;
		}
	}

}