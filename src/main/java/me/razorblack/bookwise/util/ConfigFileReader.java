package me.razorblack.bookwise.util;

import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import static me.razorblack.bookwise.constants.BaseConstants.*;

/**
 * Class to read configuration file
 */
@Slf4j
public class ConfigFileReader {
    private static final String CONFIG_FILE = SRC + SEPARATOR + MAIN + SEPARATOR + RESOURCES + SEPARATOR + CONFIG_FILE_NAME;
    public static final ConfigFileReader CONFIG = new ConfigFileReader();
    private static Properties properties = new Properties();

    private ConfigFileReader() {}

    /**
     * Method to load configurations
     */
    public static void loadConfig() {
        try (InputStream input = new FileInputStream(CONFIG_FILE)) {
            properties.load(input);
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

    /**
     * Method to get the configuration properties with default value
     * @param property Config property key
     * @param defaultValue Default value if property is not found
     * @return Config property value if present, otherwise defaultValue
     */
    public String getPropertyValue(String property, String defaultValue) {
        return properties.getProperty(property, defaultValue);
    }
}
