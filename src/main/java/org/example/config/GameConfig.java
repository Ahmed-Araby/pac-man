package org.example.config;

import java.util.HashMap;
import java.util.Map;

public class GameConfig {

    private static final Map<String, Object> configs = new HashMap<>();

    public static void load() {
        final boolean isDebugModeOn = Boolean.parseBoolean(System.getProperty("debug"));
        configs.put("debug", isDebugModeOn);
    }


    public static boolean isDebugModeOn() {
        final Object value = configs.get("debug");
        if(value == null) {
            return false;
        }
        return (boolean) value;
    }
}
