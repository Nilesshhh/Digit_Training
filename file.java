package util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CredentialReader {
    public static Map<String, String> readCredentials() throws IOException {
        Map<String, String> credentials = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader("src/resources/db_credentials.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(": ");
                if (parts.length == 2) {
                    credentials.put(parts[0].trim(), parts[1].trim());
                }
            }
        }
        return credentials;
    }
}
