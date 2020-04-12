package com.application.envfile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Map;

public final class EnvFile {

    /**
     * Load .env file as system variable
     */
    public static void load() {
        try (FileReader fs = new FileReader(".env"); BufferedReader br = new BufferedReader(fs)) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("=");
                setEnv(parts[0], parts[1]);
            }
        } catch (IOException | ReflectiveOperationException e) {
            // .env file not exists, nothing happened
        }
    }

    private static void setEnv(String key, String value) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        Class<?> processEnvironmentClass;
        Field theEnvironmentField;
        Map<String, String> env;
        Field theCaseInsensitiveEnvironmentField;
        Map<String, String> cienv;

        processEnvironmentClass = Class.forName("java.lang.ProcessEnvironment");
        theEnvironmentField = processEnvironmentClass.getDeclaredField("theEnvironment");
        theEnvironmentField.setAccessible(true);

        env = (Map<String, String>) theEnvironmentField.get(null);
        env.put(key, value);

        theCaseInsensitiveEnvironmentField = processEnvironmentClass.getDeclaredField("theCaseInsensitiveEnvironment");
        theCaseInsensitiveEnvironmentField.setAccessible(true);
        cienv = (Map<String, String>) theCaseInsensitiveEnvironmentField.get(null);
        cienv.put(key, value);
    }
}
