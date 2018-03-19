package Vibrationspotter;

import model.Persoon;
import Bcrypt.BCrypt;
public interface LoginManagementEJBLocal {

	boolean controleerpaswoord(String user, String pwd);

	
	
}
