package project;

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

private String id;

public String controle(int id){

	
	if(projectejb.checkGoedgekeurd(id)){
		HttpSession session = SessionUtils.getSession();
		session.setAttribute("idKaart", id);
		return "metingen.xhtml";
		
	}
	FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"geen geldige id!",null));
	return "kaartLogin";
	
	
}

public List<Meting> zoekMetingen(){
	HttpSession session = SessionUtils.getSession();
	int id=(int) session.getAttribute("idKaart");
	System.out.println("ketnet"+id);
	List<Meting> metingen = metingejb.findMetingenByIdProject(id);
	return metingen;

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
  

 
public String getId() {
	return id;
}

public void setId(String id) {
	this.id = id;
}



}
