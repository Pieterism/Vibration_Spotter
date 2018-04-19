package project;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.servlet.http.HttpSession;

import Vibrationspotter.MetingManagementEJBLocal;
import Vibrationspotter.ProjectManagementEJBLocal;
import model.Meting;
import model.Persoon;
import model.Project;
 
@ManagedBean(name="sorterMeting")
@ViewScoped
public class SortViewMeting implements Serializable {
     
    private List<Meting> metingen;

    @EJB
	private MetingManagementEJBLocal metingejb; 

 
    @PostConstruct
    public void init() {
    	HttpSession session = SessionUtils.getSession();
   	  	int idProject=(int) session.getAttribute("idProject");
    	metingen=metingejb.findMetingenByIdProject(idProject);
   	  	 
   	  	
        
    }


	public List<Meting> getMetingen() {
		return metingen;
	}


	public void setMetingen(List<Meting> metingen) {
		this.metingen = metingen;
	}
   


 

}
 