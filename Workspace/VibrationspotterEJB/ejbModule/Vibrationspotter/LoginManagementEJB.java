package Vibrationspotter;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;

import Vibrationspotter.BCrypt;

import java.util.ArrayList;
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



		if(personen.size()==1){
			return bcrypt.checkpw(pwd, personen.get(0).getPaswoord());
		}
		return false;
	}
	
	public boolean controleerPaswoordLeerkrachtApp(String email, String pwd) {
		Query q = em.createQuery("SELECT p FROM Persoon p WHERE p.emailadres = :email");
		q.setParameter("email", email);
		List<Persoon> personen = q.getResultList();
		List<Persoon> volledigelijst = new ArrayList<Persoon>();;
		for (int i=0; i<personen.size();i++){
			if(personen.get(i).getType().equals("Persoon")){
				volledigelijst.add(personen.get(i));
			}
		}



		if(volledigelijst.size()==1){
			return bcrypt.checkpw(pwd, volledigelijst.get(0).getPaswoord());
		}
		return false;
	}
	
}

