package Vibrationspotter;



import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import model.Project;

@Stateless
public class ProjectManagementEJB implements ProjectManagementEJBLocal{
	
	@PersistenceContext(unitName="demodb")
	private EntityManager em;
	
	@Resource
	private SessionContext ctx;

	@Override
	public Project findProject(String titel) {
		Query q = em.createQuery("SELECT p FROM Project  WHERE p.titel = : titel" );
		q.setParameter(1, titel);
		return null;
	}

	@Override
	public void addProject(Project project) {
		// TODO Auto-generated method stub
		
	}

}
