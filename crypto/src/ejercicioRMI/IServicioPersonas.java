package ejercicioRMI;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Interfaz del servicio para trabajar con personas
 * Es un CRUD
 * @author jaggarcia
 *
 */
public interface IServicioPersonas extends Remote {

	/**
	 * Crea la persona
	 * @param persona
	 * @throws RemoteException
	 */
	public EntidadPersona crearPersona (EntidadPersona persona) throws RemoteException;
	
	/**
	 * Actualiza los datos de la persona
	 * @param persona
	 * @throws RemoteException
	 */
	public void updatePersona (EntidadPersona persona) throws RemoteException;
	
	/**
	 * Borra la persona indicada
	 * @param persona
	 * @return
	 * @throws RemoteException
	 */
	public EntidadPersona deletePersona (EntidadPersona persona) throws RemoteException;
	
	/**
	 * Obtiene una lista con todas las personas
	 * @return
	 * @throws RemoteException
	 */
	public List<EntidadPersona> getAllPersonas () throws RemoteException;
}
