package util;

import java.util.Base64;

public class StringUtil {

	public static String getBase64(byte[] cipherText) {
		return Base64.getEncoder().encodeToString(cipherText);
	}

	public static String getHex(byte[] digestText) {
		StringBuffer result = new StringBuffer();
		for (byte b : digestText) {
		    result.append(String.format("%02X", b));
		}
		return result.toString();
	}

}
