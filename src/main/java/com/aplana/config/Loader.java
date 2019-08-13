package com.aplana.config;

public class Loader {

    private static RedisConfig config;

    private Loader() {

    }

    public static synchronized RedisConfig getConfig() {
        return config;
    }

    public static synchronized  void setConfig(RedisConfig map) {
        config = map;
    }
}
