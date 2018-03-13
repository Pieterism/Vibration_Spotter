package Vibrationspotter;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Named
@Stateless
public class OctaveManagementEJB implements OctaveManagementEJBLocal{

	@PersistenceContext(unitName="demodb")
	private EntityManager em;

	@EJB
	private OctaveManagementEJBLocal octaveEJB;

	@Resource
	private SessionContext ctx;

	public OctaveManagementEJB(){};
}
