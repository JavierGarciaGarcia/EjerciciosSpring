package basico.jrmi;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class HolaMundoClient {
	public static void main(String[] args) throws Exception {
		Registry registry = LocateRegistry.getRegistry();
		HolaMundo holaMundo = (HolaMundo)registry.lookup("HolaMundo");
		
		System.out.println(holaMundo.saludar("Juan"));
	}
}
