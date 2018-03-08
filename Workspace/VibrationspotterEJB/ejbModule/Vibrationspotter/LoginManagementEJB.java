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
					try {
						MessageDigest md = java.security.MessageDigest.getInstance("SHA-256");
					 
					try {
						md.update(pwd.getBytes("UTF-8"));
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
					byte[] paswoordDigest = md.digest();
					String pHash = DatatypeConverter.printBase64Binary(paswoordDigest);
					System.out.println(pHash);
					if(Leerkracht.get(0).getPaswoord().equals(pHash)){
						return true;
					}
			
					
					
					
					}
					catch (NoSuchAlgorithmException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				/*	if(Leerkracht.get(0).getPaswoord().equals(pwd)){
						return true;
					}*/
		
				}
				
			return false ;
		}
}



