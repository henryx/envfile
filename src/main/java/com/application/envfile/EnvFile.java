package com.application.envfile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public final class EnvFile {

    /**
     * Load .env file as system variable
     */
    public static void load() {
        try (FileReader fs = new FileReader(".env"); BufferedReader br = new BufferedReader(fs)) {
            String line;
            while ((line = br.readLine()) != null) {
            }
        } catch (IOException e) {
            // .env file not exists, nothing happened
        }
    }
}
