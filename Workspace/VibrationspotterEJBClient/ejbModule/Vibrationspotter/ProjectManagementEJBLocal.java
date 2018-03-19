package Vibrationspotter;

import java.util.List;

import model.Project;

public interface ProjectManagementEJBLocal {

	public int findProject(String titel);

	void addProject(Project project);

	public List<Project> findAllProjecten();

	void RemoveProject(Project project);

	void wissenProject(int id);

}
