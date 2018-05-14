package Vibrationspotter;

import java.io.IOException;
import java.util.List;

import model.Project;

/**
 * Bean to handle Person class queries and operations on database
 * 
 * @author Birgen Vermang, Thomas Bruneel, Pieter-Jan Vanhaverbeke, Pieter
 *         Vanderhaegen
 *
 */
public interface ProjectManagementEJBLocal {

	/**
	 * @param titel
	 *            project title
	 * @return project with corresponding title
	 */
	public Project findProject(String titel);

	/**
	 * @param id
	 *            project id
	 * @return project with corresponding id
	 */
	public Project findProjectById(int id);

	/**
	 * @param QR
	 *            QR string
	 * @return Project with corresponding QR string
	 */
	public Project findProjectByQR(String QR);

	/**
	 * @param email
	 *            email user
	 * @return project from user with corresponding email
	 */
	Project findProjectByEmail(String email);

	/**
	 * @param ingegevenstring
	 *            string representation of login credentials
	 * @return all projects of a user
	 */
	public String FindAllProjectsForApp(String ingegevenstring);

	/**
	 * @param project
	 *            project to add
	 */
	void addProject(Project project);

	/**
	 * @return all projects
	 */
	public List<Project> findAllProjecten();

	/**
	 * @param project
	 *            project to remove
	 */
	void RemoveProject(Project project);

	/**
	 * @param id
	 *            id of project to remove
	 */
	void wissenProject(int id);

	/**
	 * @param id
	 *            id of project you want QR code from
	 */
	void getQRCode(int id);

	/**
	 * @param qrString
	 *            string representation of QR code
	 * @return true if string matches string value of qr in database
	 * @throws IOException
	 */
	boolean checkQR(String qrString) throws IOException;

	/**
	 * @param pro
	 *            add info to existing project in database
	 */
	public void update(Project pro);

	/**
	 * @param idProject
	 *            id of project to check
	 * @return true if boolean is approved by admin
	 */
	public boolean checkGoedgekeurd(int idProject);

	/**
	 * @return all projects who are approved
	 */
	public List<Project> findGoedgekeurdeProjecten();
	
	/**
	 * @return all projects who are approved converted for REST-request to app
	 */
	public String findGoedgekeurdeProjectenAPP();

	/**
	 * @param gegevens
	 *            json representation of project
	 */
	public void ToevoegenProjectenAPP(String gegevens);

	/**
	 * @param gegevens
	 *            json representation project
	 */
	public void verwijderProjectViaApp(String gegevens);

	/**
	 * @param idPersoon
	 *            id person
	 * @return all projects of this person
	 */
	public List<Project> findMijnProjecten(int idPersoon);

	/**
	 * @param gegevens
	 *            json representation of project
	 * @return json representation of project
	 */
	public String HaalprojectviaApp(String gegevens);

	/**
	 * @param id
	 *            id project
	 * @return amount of metingen in project
	 */
	public int haalMetingenSizeProject(int id);

	/**
	 * @param idProject
	 *            id project
	 * @return json representation of Person
	 */
	public String findPersonByIdProject(int idProject);

}
