package driver;

import configuration.AddressConfiguration;
import configuration.CapabilitiesConfiguration;
import configuration.ConfigurationReader;
import configuration.EnvironmentType;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Optional;

import static java.lang.String.format;

public class DriverManager {
    private static final Logger LOG = LogManager.getRootLogger();
    private static final EnvironmentType ENVIRONMENT_TYPE = EnvironmentType.valueOf(ConfigurationReader.get().env().toUpperCase());
    private static AppiumDriver driver;

    private DriverManager() {
    }

    public static AppiumDriver getDriver() {
        if (driver == null) {
            driver = createDriver();
        }
        return driver;
    }

    public static AppiumDriver createDriver() {
        if (ENVIRONMENT_TYPE == EnvironmentType.LOCAL) {
            if ("Android".equalsIgnoreCase(ConfigurationReader.get().platformName())) {
                driver = new AndroidDriver(AddressConfiguration.getAppiumDriverLocalService(ConfigurationReader.get().appiumPort()),
                        CapabilitiesConfiguration.getLocalCapabilitiesForAndroid());
            } else if ("iOS".equalsIgnoreCase(ConfigurationReader.get().platformName())) {
                driver = new IOSDriver(AddressConfiguration.getAppiumDriverLocalService(ConfigurationReader.get().appiumPort()),
                        CapabilitiesConfiguration.getLocalCapabilitiesForIOS());
            } else {
                throw new IllegalArgumentException("Unsupported platform: " + ConfigurationReader.get().platformName());
            }
        } else {
            throw new IllegalArgumentException("Unsupported environment value: " + ENVIRONMENT_TYPE);
        }
        LOG.info("Driver is created");
        LOG.info("Environment type is {}", ENVIRONMENT_TYPE);
        return driver;
    }

    public static void quitDriver() {
        Optional.ofNullable(getDriver()).ifPresent(driver -> {
            driver.quit();
            driver = null;
            LOG.info("Driver is closed");
        });
    }
    public static void closeEmulator() {
        try {
            Runtime.getRuntime().exec(format("adb -s %s emu kill", ConfigurationReader.get().udid()));
            LOG.info("AVD is closed");
        } catch (IOException e) {
            LOG.info("AVD was not closed, message: {}", e.getMessage());
        }
    }
    public static void closeAppium() {
        AddressConfiguration.stopService();
        LOG.info("Appium server is closed");

    }

}
