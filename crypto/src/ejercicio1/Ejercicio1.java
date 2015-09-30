/**
 * 
 */
package ejercicio1;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;

import util.StringUtil;

/**
 * @author jaggarcia
 *
 */
public class Ejercicio1 {
	
	private final String pathQuijote = "D:\\Usuarios\\jaggarcia\\Desktop\\Curso Criptografia\\Proyectos\\workspace\\crypto\\quijote.txt";
	private final String pathQuijoteCifrado = "D:\\Usuarios\\jaggarcia\\Desktop\\Curso Criptografia\\Proyectos\\workspace\\crypto\\quijoteCifrado.txt";
	private final String pathQuijoteDesCifrado = "D:\\Usuarios\\jaggarcia\\Desktop\\Curso Criptografia\\Proyectos\\workspace\\crypto\\quijoteDesCifrado.txt";
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Ejercicio1 ej1 = new Ejercicio1();
		try {
			ej1.cifrarAES();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			ej1.cifrarRSA();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			ej1.getHashQuijote();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void cifrarRSA () throws NoSuchAlgorithmException, NoSuchPaddingException, IOException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		Cipher cipher = Cipher.getInstance("DESede");
		
		Key key = KeyGenerator.getInstance("DESede").generateKey();
		
		FileInputStream fis;
	    FileOutputStream fos;
	    CipherOutputStream cos;
	    
	    cipher.init(Cipher.ENCRYPT_MODE, key);
	    
	    //Ciframos
	    fis = new FileInputStream(pathQuijote);
	    fos = new FileOutputStream(pathQuijoteCifrado);
	    cos = new CipherOutputStream(fos, cipher);
	    byte[] b = new byte[8];
	    int i = fis.read(b);
	    while (i != -1) {
	        cos.write(b, 0, i);
	        i = fis.read(b);
	    }
	    cos.flush();
	    
	    //Desciframos
	    FileInputStream fis1;
	    FileOutputStream fos1;
	    CipherInputStream cis;
	    cipher.init(Cipher.DECRYPT_MODE, key);
	    
	    fis1 = new FileInputStream(pathQuijoteCifrado);
	    cis = new CipherInputStream(fis1, cipher);
	    fos1 = new FileOutputStream(pathQuijoteDesCifrado);
	    b = new byte[8];
	    i = cis.read(b);
	    while (i != -1) {
	        fos1.write(b, 0, i);
	        i = cis.read(b);
	    }
	    fos1.close();
	    cis.close();
/*
		//Descifrar
	    CipherInputStream cis1, cis2;
	    
	    fis = new FileInputStream("/tmp/a.txt");
	    cis1 = new CipherInputStream(fis, cipher1);
	    cis2 = new CipherInputStream(cis1, cipher2);
	    fos = new FileOutputStream("/tmp/b.txt");
	    byte[] b = new byte[8];
	    int i = cis2.read(b);
	    while (i != -1) {
	        fos.write(b, 0, i);
	        i = cis2.read(b);
	    }
	    fos.close();
/*
		//Obtengo el array de bytes del quijote
		byte[] quijote = this.leerQuijote();
		
		//Lo cifro
		byte[] cifrado = this.cifrarRSA(quijote, cipher, publicKey);
		byte[] descifrado = this.descifrarRSA(cifrado, cipher, privateKey);
		
		String quijoteTXTOriginal = StringUtil.getBase64(quijote);
		String quijoteTXT = StringUtil.getBase64(descifrado);
		
		System.out.println(quijoteTXTOriginal);
		System.out.println(quijoteTXT);
		*/
	}
	
	public void cifrarAES () throws Exception {
		
		Cipher cipher = Cipher.getInstance("AES");
		Key key = KeyGenerator.getInstance("AES").generateKey();		
		
		//Obtengo el array de bytes del quijote
		byte[] quijote = this.leerQuijote();
		
		//Lo cifro
		byte[] cifrado = this.cifrarAES(quijote, cipher, key);
		byte[] descifrado = this.descifrarAES(cifrado, cipher, key);
		
		String quijoteTXTOriginal = StringUtil.getBase64(quijote);
		String quijoteTXT = StringUtil.getBase64(descifrado);
		
		System.out.println(quijoteTXTOriginal);
		System.out.println(quijoteTXT);

	}
	
	private byte[] descifrarAES(byte[] cipherText, Cipher cipher, Key key) throws Exception {
		cipher.init(Cipher.DECRYPT_MODE, key);
		
		//Usamos el metodo doFinal porque es pequeño y podemos descifrar del tirón
		//Si fuera un objeto grande (fichero ...) debemos usar el metodo update
		return cipher.doFinal(cipherText);
	}

	private byte[] cifrarAES(byte[] data, Cipher cipher, Key key) throws Exception {
		cipher.init(Cipher.ENCRYPT_MODE, key);
		
		//Usamos el metodo doFinal porque es pequeño y podemos cifrar del tirón
		//Si fuera un objeto grande (fichero ...) debemos usar el metodo update
		return cipher.doFinal(data);
	}
	
	private byte[] leerQuijote () throws IOException {
		
		Path path = Paths.get(pathQuijote);
		byte[] data = Files.readAllBytes(path);
		
		return data;
	}

	private byte [] generarHashQuijote () throws NoSuchAlgorithmException, IOException {
		String ALGORITMO = "SHA1";
		MessageDigest messageDigest = MessageDigest.getInstance(ALGORITMO);
		
		byte[] quijote = this.leerQuijote();
		return messageDigest.digest(quijote);
	}
	
	public void getHashQuijote () throws NoSuchAlgorithmException, IOException {
		
		System.out.println("Hash (Hola mundo) = " + StringUtil.getHex(this.generarHashQuijote()));
	}
}
