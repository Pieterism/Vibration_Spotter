package Vibrationspotter;

import java.io.IOException;
import java.util.List;

import model.Project;

public interface ProjectManagementEJBLocal {

	public Project findProject(String titel);

	public Project findProjectById(int id);
	
	public Project findProjectByQR(String QR);
	
	Project findProjectByEmail(String email);

	public String FindAllProjectsForApp(String ingegevenstring);

	void addProject(Project project);

	public List<Project> findAllProjecten();

	void RemoveProject(Project project);

	void wissenProject(int id);

	void getQRCode(int id);

	boolean checkQR(String qrString) throws IOException;

	public void update(Project pro);
	
	public boolean checkGoedgekeurd(int idProject);
	
	public List<Project> findGoedgekeurdeProjecten();

}
