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

import Vibrationspotter.ProjectManagementEJBLocal;
import model.Persoon;
import model.Project;
 
@ManagedBean(name="sorter")
@ViewScoped
public class SortView implements Serializable {
     
    private List<Project> projecten;

    @EJB
	private ProjectManagementEJBLocal projectejb; 

 
    @PostConstruct
    public void init() {
    	System.out.println("hallo");
    	HttpSession session = SessionUtils.getSession();
   	  	boolean admin=(boolean) session.getAttribute("admin");
   	  	int id = (int) session.getAttribute(("idPersoon"));
   	  	if(admin==true){
   	  	System.out.println("admin");
   	  		projecten=projectejb.findAllProjecten();
   	  	}
   	  	else{
   	  		System.out.println("geen admin");
   	  		
   	  		List<Project> alleProjecten = projectejb.findAllProjecten();
   	  		List<Project> goedeprojecten =  new ArrayList<Project>();
   	  		for(int i=0;i<alleProjecten.size();i++){
   	  			if(alleProjecten.get(i).getIdPersoon().getIdPersoon()==id){
   	  				goedeprojecten.add(alleProjecten.get(i));
   	  			}
		}
   	  		setProjecten(goedeprojecten);
   	  }
   	  	 
   	  	
        
    }
    public void update(Project pro){
    	System.out.println(pro.isGoedgekeurd());
    	for(Project p:getProjecten()){
    		if(pro.getIdProject()==p.getIdProject()){
    			
    			if(p.isGoedgekeurd()==false){
    				p.setGoedgekeurd(true);
    			}
    			else{
    				p.setGoedgekeurd(false);
    			}
    			
    		}
    	}
    	
		projectejb.update(pro);
    	
    }


	public List<Project> getProjecten() {
		return projecten;
	}


	public void setProjecten(List<Project> projecten) {
		this.projecten = projecten;
	}
 

}
 