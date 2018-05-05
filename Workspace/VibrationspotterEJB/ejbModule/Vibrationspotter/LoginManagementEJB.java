package Vibrationspotter;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import model.Persoon;

/**
 * Bean to handle authentication with database
 * 
 * @author Birgen Vermang, Thomas Bruneel, Pieter-Jan Vanhaverbeke, Pieter
 *         Vanderhaegen
 *
 */
@Stateless
public class LoginManagementEJB implements LoginManagementEJBLocal {

	@PersistenceContext(unitName = "demodb")
	private EntityManager em;

	@Resource
	private SessionContext ctx;

	@EJB
	private BCrypt bcrypt;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * Vibrationspotter.LoginManagementEJBLocal#controleerpaswoord(java.lang.
	 * String, java.lang.String)
	 */
	public boolean controleerpaswoord(String email, String pwd) {
		/*
		 * Methode dat Passwoord en Email van Web gekregen vergelijkt met
		 * databank.
		 */
		Query q = em.createQuery("SELECT p FROM Persoon p WHERE p.emailadres = :email");
		q.setParameter("email", email);
		List<Persoon> personen = q.getResultList();

		if (personen.size() == 1) {
			return bcrypt.checkpw(pwd, personen.get(0).getPaswoord());
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * Vibrationspotter.LoginManagementEJBLocal#controleerPaswoordLeerkrachtApp(
	 * java.lang.String, java.lang.String)
	 */
	public boolean controleerPaswoordLeerkrachtApp(String email, String pwd) {
		/*
		 * Methode dat Passwoord en Email van App gekregen vergelijkt met
		 * databank (zelfde manier van APP).
		 */
		Query q = em.createQuery("SELECT p FROM Persoon p WHERE p.emailadres = :email");
		q.setParameter("email", email);
		List<Persoon> personen = q.getResultList();
		List<Persoon> volledigelijst = new ArrayList<Persoon>();
		;
		for (int i = 0; i < personen.size(); i++) {
			if (personen.get(i).getType().equals("Persoon")) {
				volledigelijst.add(personen.get(i));
			}
		}

		if (volledigelijst.size() == 1) {
			return bcrypt.checkpw(pwd, volledigelijst.get(0).getPaswoord());
		}
		return false;
	}

}
