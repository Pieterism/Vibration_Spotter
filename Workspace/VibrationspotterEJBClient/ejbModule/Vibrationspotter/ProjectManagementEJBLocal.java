package Vibrationspotter;

import model.Project;

public interface ProjectManagementEJBLocal {
	public Project findProject(String titel);

	void addProject(Project project);


}
