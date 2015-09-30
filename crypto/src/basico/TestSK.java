package basico;

import java.security.Key;
import java.security.MessageDigest;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

import util.StringUtil;

public class TestSK {
	private static final String ALGORITMO = "AES";
	private static final String PASSPHRASE = "123456";
	private static final String SALT = "123";
	private Cipher cipher;
	private Key key;

	public TestSK() throws Exception {
		//1 creamos el engine para cifrar
		//1.1 podemos elegir algorito o algoritmo/modo/padding
		//ej: AES o AES/CBC/PKCS5PADDING
		this.cipher = Cipher.getInstance(ALGORITMO);
		
		//2 creamos la clave para cifrar
		//this.key = getKey1(PASSPHRASE);
		this.key = getKey();
		
		System.out.println("key = " + StringUtil.getBase64(this.key.getEncoded()));
	}

	private Key getKey() throws Exception {
		
		//ejemplo de como generar la key para un algoritmo
		return KeyGenerator.getInstance(ALGORITMO).generateKey();
		
	}
	
	private Key getKey1(String passphrase) throws Exception {
		// otra opcion para generar la key es usar un hash a partir de una passphrase
		byte[] innerKey = (SALT + ":" + PASSPHRASE).getBytes("UTF-8");
		MessageDigest sha = MessageDigest.getInstance("SHA-1");
		innerKey = sha.digest(innerKey);
		innerKey = Arrays.copyOf(innerKey, 16); // use only first 128 bit
		
		return new SecretKeySpec(innerKey, "AES");
	}

	private Key getKey2(String passphrase) throws Exception {
		byte[] seed = new byte[16];
		seed = Arrays.copyOf(passphrase.getBytes(), 16);
				
		return new SecretKeySpec(seed, "AES");
	}

	public static void main(String[] args) throws Exception {
		new TestSK().iniciar();
	}

	private void iniciar() throws Exception {
		System.out.println("Cifrar Hola mundo");
		byte[] cipherText = cifrar("Hola mundo");
		System.out.println("Texto cifrado = " + StringUtil.getBase64(cipherText));
		byte[] plainText = descifrar(cipherText);
		
		System.out.println("Mensaje descifrado = " + new String(plainText));
	}

	private byte[] descifrar(byte[] cipherText) throws Exception {
		cipher.init(Cipher.DECRYPT_MODE, key);
		
		//Usamos el metodo doFinal porque es pequeño y podemos descifrar del tirón
		//Si fuera un objeto grande (fichero ...) debemos usar el metodo update
		return cipher.doFinal(cipherText);
	}

	private byte[] cifrar(String string) throws Exception {
		cipher.init(Cipher.ENCRYPT_MODE, key);
		
		//Usamos el metodo doFinal porque es pequeño y podemos cifrar del tirón
		//Si fuera un objeto grande (fichero ...) debemos usar el metodo update
		return cipher.doFinal(string.getBytes());
	}
}
