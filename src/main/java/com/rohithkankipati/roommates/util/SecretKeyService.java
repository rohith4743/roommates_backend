package com.rohithkankipati.roommates.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;

@Service
public class SecretKeyService {

    @Value("${jwt.secret.file}")
    private String secretKeyFilePath;

    public String getSecretKey() throws IOException {
        // Read all bytes from the file specified in the properties
        byte[] keyBytes = Files.readAllBytes(Paths.get(secretKeyFilePath));
        return new String(keyBytes).trim();  // Convert bytes to string and trim any whitespace
    }
}