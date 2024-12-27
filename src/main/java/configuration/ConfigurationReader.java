package configuration;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static constants.BaseConstants.CONFIGURATION_PROPERTIES_PATH;

public class ConfigurationReader {

    private static final Logger LOG = LogManager.getRootLogger();
    private static final Properties properties = new Properties();
    private static ConfigurationReader instance;

    private ConfigurationReader(){}

    public static ConfigurationReader get(){
        if (instance == null) {
            instance = new ConfigurationReader();
            try (FileInputStream file = new FileInputStream(CONFIGURATION_PROPERTIES_PATH)) {
                properties.load(file);
            } catch (IOException e) {
                LOG.error("Properties were not loaded");
            }

        }
        return instance;
    }

    public String platformName() {
        return properties.getProperty("platform.name");
    }

    public String platformVersion() {
        return properties.getProperty("platform.version");
    }

    public String localDeviceName() {
        return properties.getProperty("local.device.name");
    }

    public String udid() {
        return properties.getProperty("udid");
    }

    public String appiumAddress() {
        return properties.getProperty("appium.address");
    }

    public int appiumPort() {
        return NumberUtils.toInt(properties.getProperty("appium.port"));
    }

    public String browserName() {
        return properties.getProperty("browser.name");
    }
    public String env() {
        return properties.getProperty("env.type");
    }
}
