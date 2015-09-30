package ejercicioRMI;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

/**
 * Cliente del servidor de persona
 * @author jaggarcia
 *
 */
public class ClientePersona {
	
	private IServicioPersonas servicioPersonas;
	private BufferedReader br;
	
	
	public ClientePersona () throws Exception {
		
		this.iniciar();
		
	}
	
	/**
	 * Metodo para iniciar el cliente recuperando el servidor
	 * @throws Exception
	 */
	private void iniciar () throws Exception {
		

		Registry registry = LocateRegistry.getRegistry();
		this.servicioPersonas = (IServicioPersonas)registry.lookup(Constantes.NOMBRE_SERVIDOR);
		
		this.br = new BufferedReader(new InputStreamReader(System.in));
		
	}
	
	/**
	 * Crea una persona
	 * @return
	 * @throws Exception
	 */
	public EntidadPersona crearPersona () throws Exception {
		
		EntidadPersona p = new EntidadPersona(null, "Javier", "Garcia", 33);
		return this.servicioPersonas.crearPersona(p);
	}
	
	public EntidadPersona crearPersonaFromMenu () throws Exception {
		
		EntidadPersona p = this.leerPersona();
		return this.servicioPersonas.crearPersona(p);
	}
	
	/**
	 * 
	 * @param p
	 * @throws Exception
	 */
	public void actualizarPersona (EntidadPersona p) throws Exception {
		this.servicioPersonas.updatePersona(p);
	}
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<EntidadPersona> getAllPersonas () throws Exception {
		
		return this.servicioPersonas.getAllPersonas();
	}
	
	/**
	 * 
	 * @param p
	 * @return
	 * @throws Exception
	 */
	public EntidadPersona borrarPersona (EntidadPersona p) throws Exception {
		return this.servicioPersonas.deletePersona(p);
	}
	
	public String readString () throws Exception {		
		return br.readLine();
	}
	
	public EntidadPersona leerPersona () throws Exception {
		System.out.println("Introduzca los datos de la persona");
		System.out.println("Nombre");
		String nombre = this.readString();
		System.out.println("Apellidos");
		String apellidos = this.readString();
		System.out.println("Edad");
		String edad = this.readString();
		
		return new EntidadPersona(null, nombre, apellidos, new Integer (edad));
	}
	
	public void menu () throws Exception {
		
		//TODO - crear el menu
		
	}
	

	public static void main(String[] args) throws Exception {
		
		try {
			
			//Creamos el objeto
			ClientePersona cliente = new ClientePersona();
			
			//Creamos la persona
			EntidadPersona p = cliente.crearPersona();
			System.out.println("Persona creada --> " + p);
			
			//Actualizo la persona
			p.setNombre("Juan");
			p.setEdad(45);
			
			cliente.actualizarPersona(p);
			
			//Recupero las personas
			List<EntidadPersona> personas = cliente.getAllPersonas();
			System.out.println(personas);
			
			//Vuelvo a insertar la persona
			cliente.crearPersona();
			
			//Las muestro
			personas = cliente.getAllPersonas();
			System.out.println(personas);
			
			//Borro la primera persona
			cliente.borrarPersona(p);
			
			//Vuelvo a mostrar las personas
			personas = cliente.getAllPersonas();
			System.out.println(personas);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
