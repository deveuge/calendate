package com.deveuge.calendate.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class EmailUtils {
	
	public static String readBodyFile(final String path) {
        try {
        	
        	InputStream inputStream = EmailUtils.class.getClassLoader().getResourceAsStream(path);

            String body = new BufferedReader(
              new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                .lines()
                .collect(Collectors.joining("\n"));

            return body;
        }
        catch(final Exception e) {
        	String msj = "Error while reading text file " + path;
            throw new RuntimeException(msj);
        }
    }
}
