package Vibrationspotter;

import javax.annotation.Resource;
import javax.ejb.EJB;
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
public class MetingManagementEJB implements MetingManagementEJBLocal{

	@PersistenceContext(unitName="demodb")
	private EntityManager em;

	@EJB
	private PersonManagementEJBLocal personEJB;
	
	@EJB
	private ProjectManagementEJBLocal projectEJB;

	@Resource
	private SessionContext ctx;

	public MetingManagementEJB(){};

	@Override
	public Meting findMeting(String titelMeting) {
		Query q = em.createQuery("SELECT id FROM Meting m WHERE m.titel = :titel");
		q.setParameter(1, titelMeting);

		return em.find(Meting.class, titelMeting);
	}

	@Override
	public void addMeting(Meting meting) {
		em.persist(meting);
	}
	
	
	public List<Meting> findMetingenByIdProject(int idProject){
		Query q = em.createQuery("SELECT m FROM Meting m WHERE m.idProject.idProject= :fname");
		q.setParameter("fname", idProject);
		List<Meting> metingen = q.getResultList();
		return metingen;
	}
	
	public void nieuweMeting(String jsonArray){
		Project p = projectEJB.findProject("tom");
		Meting m = new Meting(0,"testV1","20/03/2018",jsonArray,"opmerking",p);
		addMeting(m);
	}
	
}
