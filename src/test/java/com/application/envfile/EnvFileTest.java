package com.application.envfile;

import org.junit.Assert;
import org.junit.Test;

public class EnvFileTest {

    @Test
    public void loadEnv() {
        EnvFile.load();

        Assert.assertEquals("ok", System.getenv("LOADED"));
    }

}