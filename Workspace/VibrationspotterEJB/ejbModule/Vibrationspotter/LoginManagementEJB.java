package Vibrationspotter;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.xml.bind.DatatypeConverter;

import Bcrypt.BCrypt;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import model.Leerkracht;
import model.Persoon;

@Stateless
public class LoginManagementEJB implements LoginManagementEJBLocal {

	@PersistenceContext(unitName = "demodb")
	private EntityManager em;

	@Resource
	private SessionContext ctx;
	private BCrypt bcrypt;

	public boolean controleerpaswoord(String emailadres, String pwd) {
		Query q = em.createQuery("SELECT p FROM Persoon p WHERE p.emailadres = :emailadres");
		q.setParameter("emailadres", emailadres);
		List<Persoon> personen = q.getResultList();
		System.out.println(personen.get(0).getPaswoord());

		if (personen.size() == 0) {
			System.out.println("ERROR! Gebruiker bestaat niet! ");

		}
		return bcrypt.checkpw(pwd, personen.get(0).getPaswoord());
	}
}
