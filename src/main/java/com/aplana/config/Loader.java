package com.aplana.config;

import java.util.Map;
import java.util.Set;

public class Loader {

    private static RedisConfig config;

    private static Map<String, Set<String>> parametersMap;

    private Loader() {
    }

    public static RedisConfig getConfig() {
        return config;
    }

    public static void setConfig(RedisConfig map) {
        config = map;
    }


    public static Map<String, Set<String>> getParametersMap() {
        return parametersMap;
    }

    public static void setParametersMap(Map<String, Set<String>> parametersMap) {
        Loader.parametersMap = parametersMap;
    }
}
