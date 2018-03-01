package Vibrationspotter;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

import model.Leerkracht;

@Stateless
public class LoginManagementEJB implements LoginManagementEJBLocal {
	
		@PersistenceContext(unitName="demodb")		
		private EntityManager em;
		
		@Resource
		private SessionContext ctx;

		@Override
		public boolean controleerpaswoord (String user, String pwd) {
			Query q = em.createQuery("SELECT p FROM Leerkracht p WHERE p.emailadres = :emailadres" );
			q.setParameter("emailadres", user);
				List<Leerkracht> Leerkracht = q.getResultList();
				if(Leerkracht.size() == 1){
					if(Leerkracht.get(0).getPaswoord().equals(pwd)){
						return true;
					}
		
				}
				
			return false ;
		}
}



