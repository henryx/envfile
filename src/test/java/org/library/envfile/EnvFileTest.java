package org.library.envfile;

import org.junit.Assert;
import org.junit.Test;

public class EnvFileTest {

    @Test
    public void loadEnv() {
        EnvFile.load();

        Assert.assertEquals("it returns ok", System.getenv("LOADED"));
        Assert.assertEquals("ok", System.getenv("LOADED2"));
    }

    @Test
    public void loadEnvByPath() {
        EnvFile.load("test.env");

        Assert.assertEquals("it returns ok", System.getenv("VARIABLE"));
        Assert.assertEquals("ok", System.getenv("VAR2"));
    }
}