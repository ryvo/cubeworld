package cz.ryvo.cubeworld;


import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

/**
 *  Singleton
 */
public class AppConfig {

    private static final String CONFIG_PROPERTIES_FILENAME = "configuration.properties";

    private static final AppConfig instance = new AppConfig();

    private int blockSize;

    /**
     * Private constructor
     */
    private AppConfig() throws RuntimeException {
        readConfigProperties();
    }

    public static AppConfig instaceOf() {
        return instance;
    }

    private void readConfigProperties() {
        PropertiesConfiguration config;
        try {
            config = new PropertiesConfiguration(CONFIG_PROPERTIES_FILENAME);
        } catch (ConfigurationException e) {
            throw new RuntimeException("Unable to read configuration properties " + CONFIG_PROPERTIES_FILENAME, e);
        }

        blockSize = config.getInt("world.blockSize", 80);
    }

    public int getBlockSize() {
        return blockSize;
    }
}
