package Vibrationspotter;

import javax.ejb.Local;

import model.Persoon;

@Local
public interface PersonManagementEJBLocal {
	public Persoon findPerson(String login);
	
	public void addPerson(String Person, String Password);
}
