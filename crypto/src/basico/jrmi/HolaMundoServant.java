package basico.jrmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class HolaMundoServant
		extends UnicastRemoteObject
		implements HolaMundo {

	private static final long serialVersionUID = -1598333638570076623L;

	public HolaMundoServant() throws RemoteException {
		super();
	}

	@Override
	public String saludar(String nombre) throws RemoteException {
		return "Hola " + nombre;
	}

}
