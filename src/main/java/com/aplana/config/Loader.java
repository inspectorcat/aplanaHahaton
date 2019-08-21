package com.aplana.config;

public class Loader {

    private static RedisConfig config;

    private Loader() {
    }

    public static RedisConfig getConfig() {
        return config;
    }

    public static void setConfig(RedisConfig map) {
        config = map;
    }
}
