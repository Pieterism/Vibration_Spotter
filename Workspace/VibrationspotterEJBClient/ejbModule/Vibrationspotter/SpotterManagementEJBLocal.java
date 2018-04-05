package Vibrationspotter;

import model.Foto;
import model.Spotter;

public interface SpotterManagementEJBLocal {
	public Foto findFoto(int idFoto);

	void addFoto(Foto foto);

	 Spotter aanmakenSpotter(String json);
}
