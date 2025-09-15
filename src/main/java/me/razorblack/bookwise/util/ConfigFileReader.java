package me.razorblack.bookwise.util;

import lombok.extern.slf4j.Slf4j;

import java.util.Properties;

@Slf4j
public class ConfigFileReader {
    private static final ConfigFileReader CONFIG = new ConfigFileReader();
    private static Properties properties = null;

    private ConfigFileReader() {}

    /**
     * Method to load configurations
     */
    public static void loadConfig() {
        try {

            log.info("Config file loaded successfully");
        } catch (Exception exception) {
            log.error("Exception occurred while loading config file: ", exception);
        }
    }

    /**
     * Method to get the configuration properties
     * @param property Config property key
     * @return Config property value if present, otherwise null
     */
    public String getPropertyValue(String property) {
        return properties.getProperty(property);
    }
}
