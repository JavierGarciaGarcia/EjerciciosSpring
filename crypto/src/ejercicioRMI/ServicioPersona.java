/**
 * 
 */
package ejercicioRMI;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.rmi.ssl.SslRMIClientSocketFactory;
import javax.rmi.ssl.SslRMIServerSocketFactory;

/**
 * @author jaggarcia
 * Servicio encargado de trabajar con la entidad persona
 *
 */
public class ServicioPersona extends UnicastRemoteObject implements IServicioPersonas {
	
	private HashMap<Long, EntidadPersona> personas;

	protected ServicioPersona() throws RemoteException {
		
		super(0, new SslRMIClientSocketFactory(), new SslRMIServerSocketFactory());
		// Creo la lista
		personas = new HashMap<Long, EntidadPersona>();
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7435458191463570242L;

	/* (non-Javadoc)
	 * @see ejercicioRMI.IServicioPersonas#crearPersona(ejercicioRMI.EntidadPersona)
	 */
	@Override
	public EntidadPersona crearPersona(EntidadPersona persona) throws RemoteException {
		
		this.trazarMensaje("crearPersona ("+persona+")");
		if (null == persona) {
			throw new RemoteException("crearPersona error: Persona no puede ser null");
		}
		if (null != persona.getId()) {
			throw new RemoteException("crearPersona error: el id de la persona no puede ser distinto de null");
		}
		
		persona.setId(new Long (this.personas.size()));
		this.personas.put(persona.getId(), persona);
		
		return persona;

	}

	/* (non-Javadoc)
	 * @see ejercicioRMI.IServicioPersonas#updatePersona(ejercicioRMI.EntidadPersona)
	 */
	@Override
	public void updatePersona(EntidadPersona persona) throws RemoteException {
		
		this.trazarMensaje("updatePersona ("+persona+")");
		EntidadPersona p = this.buscaPersona(persona);
		if (p != null) {
			this.personas.remove(p.getId());			
		}
		this.personas.put(p.getId(), persona);

	}

	/* (non-Javadoc)
	 * @see ejercicioRMI.IServicioPersonas#deletePersona(ejercicioRMI.EntidadPersona)
	 */
	@Override
	public EntidadPersona deletePersona(EntidadPersona persona) throws RemoteException {

		this.trazarMensaje("deletePersona ("+persona+")");
		EntidadPersona p = this.buscaPersona(persona);
		if (p != null) {
			this.personas.remove(p.getId());
		}
		return p;
	}

	/* (non-Javadoc)
	 * @see ejercicioRMI.IServicioPersonas#getAllPersonas()
	 */
	@Override
	public List<EntidadPersona> getAllPersonas() throws RemoteException {
		this.trazarMensaje("getAllPersonas");
		return new ArrayList<EntidadPersona>(this.personas.values());
	}
	
	/**
	 * Busca la persona en la lista de personas
	 * @param p
	 * @return
	 */
	private EntidadPersona buscaPersona (EntidadPersona p) throws RemoteException {
		
		if (null == p) {
			throw new RemoteException("Error buscaPersona. La persona no puede ser null");
		}
		
		if (null == p.getId()) {
			return null;
		}
		else {
			return this.personas.get(p.getId());
		}
	}
	
	private void trazarMensaje (String mensaje) {
		System.out.println("Servicio Personas --> " + mensaje);
	}

}
