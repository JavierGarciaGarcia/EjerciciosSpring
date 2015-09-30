package util;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

public class ArchivoUtil {

	public static void escribirArchivo(byte[] text, String archivo) throws Exception {
		Files.deleteIfExists(new File(archivo).toPath());
		Files.write(new File(archivo).toPath(), text, StandardOpenOption.CREATE_NEW);
	}

	public static byte[] leerArchivo(String archivo) throws Exception {
		return Files.readAllBytes(new File(archivo).toPath());
	}

}
