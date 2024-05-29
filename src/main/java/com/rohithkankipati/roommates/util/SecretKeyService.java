package com.rohithkankipati.roommates.util;
//

//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//import java.io.IOException;
//
//@Service
//public class SecretKeyService {
//
//    @Value("${jwt.secret.file}")
//    private String secretKeyFilePath;
//
//    public String getSecretKey() throws IOException {
//        // Read all bytes from the file specified in the properties
//        byte[] keyBytes = Files.readAllBytes(Paths.get(secretKeyFilePath));
//        return new String(keyBytes).trim();  // Convert bytes to string and trim any whitespace
//    }
//}

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
public class SecretKeyService {

    @Value("${jwt.secret.file}")
    private Resource secretKeyResource;

    public String getSecretKey() throws IOException {
	if (!secretKeyResource.exists()) {
	    throw new IOException("Secret key file not found in classpath");
	}

	try (InputStream inputStream = secretKeyResource.getInputStream()) {
	    byte[] keyBytes = inputStream.readAllBytes();
	    return new String(keyBytes).trim(); // Convert bytes to string and trim any whitespace
	}
    }
}
