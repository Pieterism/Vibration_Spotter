package Vibrationspotter;

import model.Leerkracht;

public interface LeerkrachtManagementEJBLocal {
	public Leerkracht findLeerkracht(int idLeerkracht);

	void addLeerkracht(Leerkracht leerkracht);


}
