package Vibrationspotter;

import model.Foto;

public interface SpotterManagementEJBLocal {
	public Foto findFoto(int idFoto);

	void addFoto(Foto foto);

	 void aanmakenSpotter(String json);
}
