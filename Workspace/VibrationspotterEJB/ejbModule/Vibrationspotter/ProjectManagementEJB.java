package Vibrationspotter;



import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

import model.Meting;
import model.Project;
@Named
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
		em.persist(project);
		
	}
	
	  public List<Project> findAllProjecten() {
		  Query q = em.createQuery("SELECT p FROM Project p ORDER BY p.idProject ASC");
		  List<Project> projecten = q.getResultList();
		return projecten;
		  }

}
