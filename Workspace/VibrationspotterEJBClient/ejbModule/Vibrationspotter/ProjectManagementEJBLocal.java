package Vibrationspotter;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.ejb.Schedule;

import model.Project;

public interface ProjectManagementEJBLocal {

	public Project findProject(String titel);

	public Project findProjectById(int id);
	
	public String FindAllProjectsForApp(String ingegevenstring);

	void addProject(Project project);

	public List<Project> findAllProjecten();

	void RemoveProject(Project project);

	void wissenProject(int id);

	void getQRCode(int id);

	String checkQR(File qrCodeimage) throws IOException;

}
