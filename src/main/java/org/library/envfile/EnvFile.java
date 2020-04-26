package org.library.envfile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Map;

public final class EnvFile {

    /**
     * Load .env file as system variable
     */
    public static void load() {
        load(".env");
    }

    /**
     * Load env file as system variable
     *
     * @param envfile Path of the env file
     */
    public static void load(String envfile) {
        try (FileReader fs = new FileReader(envfile); BufferedReader br = new BufferedReader(fs)) {
            String line, value;

            while ((line = br.readLine()) != null) {
                try {
                    if (!line.equals("") && !line.startsWith("#")) {
                        String[] parts = line.split("=");
                        if (parts[1].startsWith("\"") || parts[1].startsWith("'")) {
                            value = parts[1].substring(1, parts[1].length() - 1);
                        } else {
                            value = parts[1];
                        }
                        setEnv(parts[0], value);
                    }
                } catch (Exception e) {
                    // something went wrong when load variable, ignoring it
                }
            }
        } catch (IOException e) {
            // .env file not exists, nothing happened
        }
    }

    private static void setEnv(String key, String value) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        if (System.getProperty("os.name").startsWith("Windows")) {
            setEnvWindows(key, value);
        } else {
            // TODO: check if this method works on other POSIX systems
            setEnvLinux(key, value);
        }
    }

    private static void setEnvWindows(String key, String value) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        Class<?> processEnvironmentClass;
        Field theEnvironmentField;
        Map<String, String> env;
        Field theCaseInsensitiveEnvironmentField;
        Map<String, String> cienv;
        boolean accessible;

        processEnvironmentClass = Class.forName("java.lang.ProcessEnvironment");
        theEnvironmentField = processEnvironmentClass.getDeclaredField("theEnvironment");

        accessible = theEnvironmentField.isAccessible();
        theEnvironmentField.setAccessible(true);

        env = (Map<String, String>) theEnvironmentField.get(null);
        env.put(key, value);
        theEnvironmentField.setAccessible(accessible);

        theCaseInsensitiveEnvironmentField = processEnvironmentClass.getDeclaredField("theCaseInsensitiveEnvironment");
        accessible = theCaseInsensitiveEnvironmentField.isAccessible();
        theCaseInsensitiveEnvironmentField.setAccessible(true);

        cienv = (Map<String, String>) theCaseInsensitiveEnvironmentField.get(null);
        cienv.put(key, value);
        theCaseInsensitiveEnvironmentField.setAccessible(accessible);
    }

    private static void setEnvLinux(String key, String value) throws NoSuchFieldException, IllegalAccessException {
        Class[] classes;
        Map<String, String> env;
        Field field;
        Object obj;
        Map<String, String> map;
        boolean accessible;

        classes = Collections.class.getDeclaredClasses();
        env = System.getenv();

        for (Class cl : classes) {
            if ("java.util.Collections$UnmodifiableMap".equals(cl.getName())) {
                field = cl.getDeclaredField("m");
                accessible = field.isAccessible();
                field.setAccessible(true);

                obj = field.get(env);
                map = (Map<String, String>) obj;
                map.put(key, value);

                field.setAccessible(accessible);
            }
        }
    }
}
