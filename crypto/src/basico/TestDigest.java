package basico;

import java.security.MessageDigest;

import util.StringUtil;

public class TestDigest {
	private static final String ALGORITMO = "SHA1";
	private MessageDigest messageDigest;

	public TestDigest() throws Exception {
		this.messageDigest = MessageDigest.getInstance(ALGORITMO);
	}

	public static void main(String[] args) throws Exception {
		new TestDigest().iniciar();
	}

	private void iniciar() throws Exception {
		byte[] digestText = hash("Hola mundo".getBytes());

		System.out.println("Hash (Hola mundo) = " + StringUtil.getHex(digestText));
		System.out.println("Long hash (Hola mundo) = " + StringUtil.getHex(digestText).length());
	}

	private byte[] hash(byte[] plainText) throws Exception {
		return messageDigest.digest(plainText);
	}

}