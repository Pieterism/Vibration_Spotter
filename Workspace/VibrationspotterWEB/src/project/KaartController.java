package project;

import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import Vibrationspotter.MetingManagementEJBLocal;
import Vibrationspotter.ProjectManagementEJBLocal;
import model.Meting;
import model.Persoon;
import model.Project;

@Named
@ViewScoped
public class KaartController implements Serializable {

private static final long serialVersionUID = 1L;


@EJB
private ProjectManagementEJBLocal projectejb;

@EJB
private MetingManagementEJBLocal metingejb;

private int id;

private List<Meting> metingen = new ArrayList<Meting>();
private Project project;



public List<Meting> zoekMetingen(){
	boolean geldig=projectejb.checkGoedgekeurd(id);
	if(geldig){
		return metingen;
	}
	return null;

}

public String toonGrafiek(Meting m) throws UnsupportedEncodingException{
	HttpSession session = SessionUtils.getSession();
	int idmeting=m.getIdMeting();
	session.setAttribute("idMeting", idmeting);
	if(metingejb.zoekDataset1(idmeting)==null||metingejb.zoekDataset2(idmeting)==null){
		return null;
	}
	return "grafiek.xhtml";
}

public void findMetingenByIdProject(){
 	int id=getId();
 	metingen=metingejb.findMetingenByIdProject(id);
}

public Project ophalenProject(){
	boolean geldig=projectejb.checkGoedgekeurd(id);
	if(geldig){
		project=projectejb.findProjectById(id);
	 	return project;
		
	}
	return null;

}




public int getId() {
	return id;
}

public void setId(int id) {
	this.id = id;
}
  

 




}
