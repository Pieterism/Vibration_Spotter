package Vibrationspotter;

import model.Project;

public interface ProjectManagementEJBLocal {
	public Project findProject(int idProject);

	void addProject(Project project);


}
