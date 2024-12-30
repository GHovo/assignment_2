package configuration;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static constants.BaseConstants.CONFIGURATION_PROPERTIES_PATH;

@Log4j2
public class ConfigurationReader {
    private static final Properties properties = new Properties();
    private static ConfigurationReader instance;

    private ConfigurationReader(){}

    public static ConfigurationReader get(){
        if (instance == null) {
            instance = new ConfigurationReader();
            try (FileInputStream file = new FileInputStream(CONFIGURATION_PROPERTIES_PATH)) {
                properties.load(file);
            } catch (IOException e) {
                log.error("Properties were not loaded");
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

    public String browserstackUser() {
        return properties.getProperty("browserstack.user");
    }

    public String browserstackKey() {
        return properties.getProperty("browserstack.key");
    }

    public String browserstackPlatformName() {
        return properties.getProperty("browserstack.platformName");
    }

    public String browserstackPlatformVersion() {
        return properties.getProperty("browserstack.platformVersion");
    }

    public String browserstackDeviceName() {
        return properties.getProperty("browserstack.deviceName");
    }
}
