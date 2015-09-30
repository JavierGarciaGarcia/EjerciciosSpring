package basico;

import java.io.FileInputStream;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.Certificate;

public class TestSignature {
	private static final String ALGORITMO_CIFRADO = "RSA"; 
	private static final String ALGORITMO_HASH = "SHA1"; 
	private Signature signature;
	private PublicKey publicKey;
	private PrivateKey privateKey;

	public TestSignature() throws Exception {
		this.signature = Signature.getInstance(ALGORITMO_HASH + "with" + 
				ALGORITMO_CIFRADO);
		generateKeys1();
	}
	
	private void generateKeys1() throws Exception {
		FileInputStream is = new FileInputStream("D:\\Usuarios\\jaggarcia\\Desktop\\Curso Criptografia\\keystore.jks");
		KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
		keystore.load(is, "170801Agosto".toCharArray());

		Key key = keystore.getKey("mycert", "170801Agosto".toCharArray());
		if (key instanceof PrivateKey) {
			// Get certificate of public key
			Certificate cert = keystore.getCertificate("mycert");
			// Get public key
			PublicKey publicKey = cert.getPublicKey();

			// Return a key pair
			KeyPair keyPair = new KeyPair(publicKey, (PrivateKey) key);
			this.publicKey = keyPair.getPublic();
			this.privateKey = keyPair.getPrivate();
		}
	}

	private void generateKeys() throws Exception {
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ALGORITMO_CIFRADO);
		KeyPair keyPair = keyPairGenerator.generateKeyPair();
		this.publicKey = keyPair.getPublic();
		this.privateKey = keyPair.getPrivate();
	}
	
	public static void main(String[] args) throws Exception {
		new TestSignature().iniciar();
	}

	private void iniciar() throws Exception {
		System.out.println("Firmando Hola mundo");
		String text = "Hola mundo";
		
		byte[] signatureText = firmar(text.getBytes());
		System.out.println("Firma = " + new String(signatureText));

		if (verficar(text.getBytes(), signatureText))
			System.out.println("El documento es válido");
		else
			System.out.println("El documento es inválido");
	}

	private boolean verficar(byte[] text, byte[] signatureText) throws Exception {
		signature.initVerify(this.getPublicKey());
		signature.update(text);
		return signature.verify(signatureText);
	}

	private byte[] firmar(byte[] sourceText) throws Exception {
		signature.initSign(this.getPrivateKey());
		signature.update(sourceText);
		return signature.sign();
	}
	
	private PrivateKey getPrivateKey () throws Exception {
		
		FileInputStream is = new FileInputStream("D:\\Usuarios\\jaggarcia\\Desktop\\Curso Criptografia\\keystore.jks");
		KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
		keystore.load(is, "170801Agosto".toCharArray());

		return (PrivateKey)keystore.getKey("myownkey", "200382Marzo".toCharArray());
	}
	
	private PublicKey getPublicKey () throws Exception {
		
		FileInputStream is = new FileInputStream("D:\\Usuarios\\jaggarcia\\Desktop\\Curso Criptografia\\keystore.jks");
		KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
		keystore.load(is, "170801Agosto".toCharArray());
		
		Certificate cert = keystore.getCertificate("myownkey");
		return cert.getPublicKey();
	}

}
