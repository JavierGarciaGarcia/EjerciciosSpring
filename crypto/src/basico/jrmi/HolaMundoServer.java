package basico.jrmi;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class HolaMundoServer {
	private static final int PUERTO = 1099;

	public static void main(String[] args) throws Exception {
				
		/*
		 * Existe otra opción que es levantar el registro rmi aparte
		 * Si lo hacemos así, en vez de crear un registro lo buscamos con lookup 
		 */
		Registry registry = 
				LocateRegistry.createRegistry(PUERTO);
		System.out.println("Iniciado HolaMundoServer...");
		registry.rebind("HolaMundo", new HolaMundoServant());
	}
}
