package project;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;

import javax.ejb.EJB;

import Vibrationspotter.MetingManagementEJBLocal;
import Vibrationspotter.PersonManagementEJBLocal;

import model.Meting;
import model.Persoon;
import model.Project;

import java.util.List;
import java.util.ArrayList;

// info over meting ophalen en metingen wissen

/**
 * Class to implement Meting management. Allows to fetch metingen, show graphs,
 * and many more.
 * 
 * @author Birgen Vermang, Thomas Bruneel, Pieter-Jan Vanhaverbeke, Pieter
 *         Vanderhaegen
 *
 */
@Named
@ViewScoped
public class MetingController implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private MetingManagementEJBLocal metingejb;

	@EJB
	private PersonManagementEJBLocal persoonejb;

	private Meting meting = new Meting();
	private Persoon persoon = new Persoon();

	private Project project = new Project();
	private List<Meting> metingen = new ArrayList<Meting>();

	public String submit() {
		metingejb.addMeting(meting);
		persoonejb.addPersoon(persoon);
		return "succes.xhtml";
	}

	public void findMetingenByIdProject() {
		HttpSession session = SessionUtils.getSession();
		int idProject = 0;
		if (session.getAttribute("idProject") != null) {
			idProject = (int) session.getAttribute("idProject");
		}
		metingen = metingejb.findMetingenByIdProject(idProject);
		// metingen=metingejb.findMetingenByIdProject(project.getIdProject());
	}

	public void gaTerugNaarMetingen() throws IOException {
		HttpSession session = SessionUtils.getSession();
		int idProject = (int) session.getAttribute("idProject");
		FacesContext.getCurrentInstance().getExternalContext().redirect("metingen.xhtml?id=" + idProject);
	}

	public String toonGrafiek(Meting m) throws UnsupportedEncodingException {
		HttpSession session = SessionUtils.getSession();
		int idmeting = m.getIdMeting();
		session.setAttribute("idMeting", idmeting);
		if (metingejb.zoekDataset1(idmeting) == null || metingejb.zoekDataset2(idmeting) == null) {
			return null;
		}
		return "grafiek";
	}

	public void wissen(Meting met) throws IOException {
		HttpSession session = SessionUtils.getSession();
		int idProject = (int) session.getAttribute("idProject");
		metingejb.wissenMeting(met.getIdMeting());
		FacesContext.getCurrentInstance().getExternalContext().redirect("metingen.xhtml?id=" + idProject);
	}

	public Meting getMeting() {
		return meting;
	}

	public void setMeting(Meting meting) {
		this.meting = meting;
	}

	public Persoon getPersoon() {
		return persoon;
	}

	public void setPersoon(Persoon persoon) {
		this.persoon = persoon;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public List<Meting> getMetingen() {
		return metingen;
	}

	public void setMetingen(List<Meting> metingen) {
		this.metingen = metingen;
	}

	public String toonFoto(Meting m) {
		HttpSession session = SessionUtils.getSession();
		int idmeting = m.getIdMeting();
		session.setAttribute("idMeting", idmeting);
		return "foto.xhtml";

	}

}