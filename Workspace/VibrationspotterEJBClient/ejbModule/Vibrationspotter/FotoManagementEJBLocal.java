package Vibrationspotter;

import model.Foto;

public interface FotoManagementEJBLocal {
	public Foto findFoto(int idFoto);

	void addFoto(Foto foto);

}
