package project;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import Vibrationspotter.MetingManagementEJBLocal;
import Vibrationspotter.ProjectManagementEJBLocal;
import model.Project;

@Named
@ViewScoped
public class ProjectController implements Serializable {

private static final long serialVersionUID = 1L;

@EJB
private ProjectManagementEJBLocal projectejb;

private Project project=new Project();



public void submit() {
projectejb.addProject(project);
}

}
