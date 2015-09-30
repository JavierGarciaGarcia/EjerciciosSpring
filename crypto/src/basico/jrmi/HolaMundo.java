package basico.jrmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface HolaMundo extends Remote {
	String saludar(String nombre) throws RemoteException;
}
