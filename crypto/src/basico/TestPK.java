package basico;

import java.io.FileInputStream;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;

import javax.crypto.Cipher;

import util.StringUtil;

/**
 * $ keytool -genkeypair -keystore keystore.jks -keyalg rsa
 */
public class TestPK {
	private static final String ALGORITMO = "RSA"; // http://docs.oracle.com/javase/7/docs/technotes/guides/security/StandardNames.html#impl
	private static final String KEYSTORE = "file/keystore.jks";
	private static final String KEYSTORE_PASSWORD = "123456";
	private static final String PRIVATE_KEY_PASSWORD = KEYSTORE_PASSWORD;
	private static final String KEYS_ALIAS = "mykey";
	private Cipher cipher;
	private Key publicKey;
	private Key privateKey;

	public TestPK() throws Exception {
		this.cipher = Cipher.getInstance(ALGORITMO);
		//generateKeys();
		generateKeys1();
	}

	private void generateKeys() throws Exception {
		KeyPairGenerator keyPairGenerator = KeyPairGenerator
				.getInstance(ALGORITMO);
		KeyPair keyPair = keyPairGenerator.generateKeyPair();
		this.publicKey = keyPair.getPublic();
		this.privateKey = keyPair.getPrivate();
	}

	private void generateKeys1() throws Exception {
		FileInputStream is = new FileInputStream(KEYSTORE);
		KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
		keystore.load(is, KEYSTORE_PASSWORD.toCharArray());

		Key key = keystore.getKey(KEYS_ALIAS, PRIVATE_KEY_PASSWORD.toCharArray());
		if (key instanceof PrivateKey) {
			// Get certificate of public key
			Certificate cert = keystore.getCertificate(KEYS_ALIAS);
			// Get public key
			PublicKey publicKey = cert.getPublicKey();

			// Return a key pair
			KeyPair keyPair = new KeyPair(publicKey, (PrivateKey) key);
			this.publicKey = keyPair.getPublic();
			this.privateKey = keyPair.getPrivate();
		}
	}

	public static void main(String[] args) throws Exception {
		new TestPK().iniciar();
	}

	private void iniciar() throws Exception {
		System.out.println("Cifrar Hola mundo");
		String text = "Hola mundo";

		byte[] cipherText = cifrar(text);
		System.out.println("Texto cifrado = "
				+ StringUtil.getBase64(cipherText));

		byte[] plainText = descifrar(cipherText);
		System.out.println("Mensaje descifrado = " + new String(plainText));
	}

	private byte[] descifrar(byte[] cipherText) throws Exception {
		cipher.init(Cipher.DECRYPT_MODE, publicKey);
		return cipher.doFinal(cipherText);
	}

	private byte[] cifrar(String string) throws Exception {
		cipher.init(Cipher.ENCRYPT_MODE, privateKey);
		return cipher.doFinal(string.getBytes());
	}
}
