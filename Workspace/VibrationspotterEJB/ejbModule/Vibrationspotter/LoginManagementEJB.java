package Vibrationspotter;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import Vibrationspotter.BCrypt;
import java.util.List;
import model.Persoon;

@Stateless
public class LoginManagementEJB implements LoginManagementEJBLocal {

	@PersistenceContext(unitName = "demodb")
	private EntityManager em;

	@Resource
	private SessionContext ctx;
	@EJB
	private BCrypt bcrypt;

	public boolean controleerpaswoord(String email, String pwd) {
		Query q = em.createQuery("SELECT p FROM Persoon p WHERE p.emailadres = :email");
		q.setParameter("email", email);
		List<Persoon> personen = q.getResultList();

<<<<<<< HEAD
		if (personen.size() == 1) {
			System.out.println(pwd);
			System.out.println(personen.get(0).getPaswoord());
=======
		if(personen.size()==1){
>>>>>>> master
			return bcrypt.checkpw(pwd, personen.get(0).getPaswoord());
		}
		return false;
	}
}
