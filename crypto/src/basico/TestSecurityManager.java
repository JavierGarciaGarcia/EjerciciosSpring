package basico;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.security.AccessControlException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.PropertyPermission;

/**
 * -Djava.security.manager -Djava.security.policy=file/test.policy
 */
public class TestSecurityManager {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws FileNotFoundException {
		SecurityManager sm = System.getSecurityManager();

		// Is there a SecurityManger registered?
		System.out.println("SecurityManager: " + sm);

		// Checking if we can open a file for reading
		new FileInputStream("file/quijote.txt");
		System.out.println("File successfully opened for reading");

		new FileOutputStream("file/quijote.txt");
		System.out.println("File successfully opened for writing");

		// Checking if we can access a vm property
		System.out.println(System.getProperty("file.encoding"));

		PropertyPermission propertyPermission = new PropertyPermission(
				"file.encoding", "read");
		try {
			if (sm != null)
				sm.checkPermission(propertyPermission);
			System.out.println("Tiene acceso a la propiedad");
		} catch (AccessControlException e) {
			System.err.println("No tiene acceso a la propiedad");
		}

		try {
			AccessController.checkPermission(propertyPermission);
			System.out.println("Tiene acceso a la propiedad");
		} catch (AccessControlException e) {
			System.err.println("No tiene acceso a la propiedad");
		}

		String propiedad = AccessController
				.doPrivileged(new PrivilegedAction<String>() {

					@Override
					public String run() {
						return System.getProperty("file.encoding");
					}

				});

		System.out.println("El valor de la propiedad es: " + propiedad);
	}
}