package Vibrationspotter;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.xml.bind.DatatypeConverter;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import model.Leerkracht;
import model.Persoon;


@Stateless
public class LoginManagementEJB implements LoginManagementEJBLocal {
	
		@PersistenceContext(unitName="demodb")		
		private EntityManager em;
		
		@Resource
		private SessionContext ctx;
		private BCrypt bcrypt;

		
		public boolean controleerpaswoord (String user, String pwd) {
			Query q = em.createQuery("SELECT p FROM Persoon p WHERE p.emailadres = :emailadres" );
			q.setParameter("emailadres", user);
				List<Persoon> personen = q.getResultList();
				if(personen.size() == 1){
					if(pwd.equals(bcrypt.checkpw(pwd,personen.get(0).getPaswoord()))){
						return true;		
						
					}
					else{
						return false;
					}
					}
				

				
			return false ;
		}
}



