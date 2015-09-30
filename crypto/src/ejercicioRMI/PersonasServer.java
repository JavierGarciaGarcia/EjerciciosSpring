/**
 * 
 */
package ejercicioRMI;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * @author jaggarcia
 *
 * 1
 */
public class PersonasServer {
	
	//private final static Integer PUERTO = 1099;
	
	public static void main (String [] args) {
		
		try {
			/*
			 * Existe otra opción que es levantar el registro rmi aparte
			 * Si lo hacemos así, en vez de crear un registro lo buscamos con lookup 
			 */
			Registry registry = 
					LocateRegistry.createRegistry(Constantes.PUERTO_SERVIDOR);
			System.out.println("Iniciado Servidor Personas ...");
			registry.rebind(Constantes.NOMBRE_SERVIDOR, new ServicioPersona());
			
		} catch (Exception e) {
			
			System.err.println("Error al iniciar el servidor");
			e.printStackTrace();
		}
	}

}
