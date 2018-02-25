package Vibrationspotter;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import model.Foto;

@Stateless
public class FotoManagementEJB implements FotoManagementEJBLocal{

	@PersistenceContext(unitName="demodb")		
	private EntityManager em;
	
	@Resource
	private SessionContext ctx;

	@Override
	public Foto findFoto(int idFoto) {
		Query q = em.createQuery("SELECT p FROM Foto  WHERE p.idFoto = : idFoto" );
		q.setParameter(1, idFoto);
		return null;
	}

	@Override
	public void addFoto(Foto foto) {
		// TODO Auto-generated method stub
		
	} 
	
}


