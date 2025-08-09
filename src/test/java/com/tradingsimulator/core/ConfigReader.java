package com.tradingsimulator.core;



import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {
    private static final Properties p = new Properties();

    static {
        try (InputStream is = ConfigReader.class.getClassLoader()
                .getResourceAsStream("config/test.properties")) {
            p.load(is);
        } catch (Exception e) {
            throw new RuntimeException("Config load failed", e);
        }
    }

    public static String get(String key) {
        String val = System.getProperty(key);
        if (val != null && !val.isBlank()) return val;
        val = System.getenv(key);
        if (val != null && !val.isBlank()) return val;
        val = p.getProperty(key);
        return val == null ? "" : val;
    }

    public static boolean getBool(String key) {
        return Boolean.parseBoolean(get(key));
    }

    public static double getDouble(String key) {
        return Double.parseDouble(get(key));
    }

    public static int getInt(String key) {
        return Integer.parseInt(get(key));
    }
}
