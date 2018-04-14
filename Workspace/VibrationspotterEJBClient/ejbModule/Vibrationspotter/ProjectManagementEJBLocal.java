package Vibrationspotter;

import java.util.List;

import javax.ejb.Schedule;

import model.Project;

public interface ProjectManagementEJBLocal {

	public Project findProject(String titel);
	
	public Project findProjectById(int id);

	void addProject(Project project);

	public List<Project> findAllProjecten();

	void RemoveProject(Project project);

	void wissenProject(int id);

	@Schedule(second="*", minute= "*", hour="*")
	void refreshQR();

	void getQRCode(int id);

}
