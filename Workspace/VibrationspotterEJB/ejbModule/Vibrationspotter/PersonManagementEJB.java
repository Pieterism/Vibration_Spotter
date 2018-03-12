package Vibrationspotter;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.Query;

import com.sun.corba.se.pept.transport.Connection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import model.Meting;
import model.Persoon;
import model.Project;


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

	public void HashPassword(String s, String id) {
		// TODO
	}

	@Override
	public void addPersoon(Persoon p) {
		em.persist(p);
		
	}
	public Persoon findPersoonByEmail(String email){
			Query q = em.createQuery("SELECT p FROM Persoon p WHERE p.emailadres = :emailadres");
			q.setParameter("emailadres", email);
			List<Persoon> personen = q.getResultList();
			Persoon p=personen.get(0);
			return p;
		}
	
		
	}


