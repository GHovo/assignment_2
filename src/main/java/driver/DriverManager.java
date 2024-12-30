package driver;

import configuration.AddressConfiguration;
import configuration.CapabilitiesConfiguration;
import configuration.ConfigurationReader;
import configuration.enums.EnvironmentType;
import configuration.enums.PlatformType;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.util.Optional;

import static java.lang.String.format;

@Log4j2
public class DriverManager {
    private static final EnvironmentType ENVIRONMENT_TYPE = EnvironmentType.valueOf(ConfigurationReader.get().env().toUpperCase());
    private static final PlatformType PLATFORM_TYPE = PlatformType.valueOf(ConfigurationReader.get().browserName().toUpperCase());
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
        switch (ENVIRONMENT_TYPE) {
            case LOCAL:
                switch (PLATFORM_TYPE) {
                    case ANDROID:
                        driver = new AndroidDriver(AddressConfiguration.getURL("http://127.0.0.1:4723/wd/hub"),
                                CapabilitiesConfiguration.getLocalCapabilitiesForAndroid());
                        break;
                    case IOS:
                        driver = new IOSDriver(AddressConfiguration.getAppiumDriverLocalService(ConfigurationReader.get().appiumPort()),
                                CapabilitiesConfiguration.getLocalCapabilitiesForIOS());
                        break;
                    default:
                        throw new IllegalArgumentException("Unsupported platform: " + ConfigurationReader.get().platformName());
                }
                break;
            case BROWSERSTACK:

                driver = new AndroidDriver(AddressConfiguration.getURL("http://hub-cloud.browserstack.com/wd/hub"),
                                CapabilitiesConfiguration.getLocalCapabilitiesForBrowserStack());


            default:
                throw new IllegalArgumentException("Unsupported environment value: " + ENVIRONMENT_TYPE);
        }
        log.info("Driver is created");
        log.info("Environment type is {}", ENVIRONMENT_TYPE);
        return driver;
    }

    public static void quitDriver() {
        Optional.ofNullable(getDriver()).ifPresent(driver -> {
            driver.quit();
            driver = null;
            log.info("Driver is closed");
        });
    }
    public static void closeEmulator() {
        try {
            Runtime.getRuntime().exec(format("adb -s %s emu kill", ConfigurationReader.get().udid()));
            log.info("AVD is closed");
        } catch (IOException e) {
            log.info("AVD was not closed, message: {}", e.getMessage());
        }
    }
    public static void closeAppium() {
        AddressConfiguration.stopService();
        log.info("Appium server is closed");

    }

}
