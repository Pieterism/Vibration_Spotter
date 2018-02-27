package Vibrationspotter;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

import model.Foto;
import model.Leerkracht;

@Stateless
public class LoginManagementEJB implements LoginManagementEJBLocal {
	
		@PersistenceContext(unitName="demodb")		
		private EntityManager em;
		
		@Resource
		private SessionContext ctx;

		@Override
		public boolean controleerpaswoord (String user, String pwd) {
			Query q = em.createQuery("SELECT p FROM Leerkracht p WHERE p.email = :email AND p.studentPaswoord = :studentPaswoord" );
		//	q.setParameter(1, idFoto);
		//	q.setParameter(2, studentPaswoord)
			List<Leerkracht> leerkracht = q.getResultList();
			if(leerkracht.size() == 1){
				if(leerkracht.get(0).getStudentPaswoord()==pwd){
					return true;
				};
				
				
				
			}
			return false ;
		}
}



