package Vibrationspotter;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.Query;

import com.sun.corba.se.pept.transport.Connection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import model.Persoon;

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
		Query q = em.createQuery(
				"INSERT INTO Persoon(voornaam, achternaam, paswoord, emailadres, admin,salt) VALUES(?,?,?,?,?,?)");
		q.setParameter(1, p.getVoornaam());
		q.setParameter(2, p.getAchternaam());
		q.setParameter(3, p.getPaswoord());
		q.setParameter(4, p.getEmailadres());
		q.setParameter(5, p.isAdmin());
		q.setParameter(6, p.getSalt());
		q.executeUpdate();

	}

}
