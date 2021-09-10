package com.deveuge.calendate.utils;

import java.net.URI;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class EmailUtils {
	
	public static String readBodyFile(final URI path) {
        try {
            final byte[] encoded = Files.readAllBytes(Paths.get(path));
            return new String(encoded, Charset.forName("UTF-8"));
        }
        catch(final Exception e) {
        	String msj = "Error while reading text file " + path;
            throw new RuntimeException(msj);
        }
    }
}
