package configuration;

import org.openqa.selenium.remote.DesiredCapabilities;
import static io.appium.java_client.remote.AndroidMobileCapabilityType.*;
import static io.appium.java_client.remote.MobileCapabilityType.*;

public class CapabilitiesConfiguration {

    private CapabilitiesConfiguration(){}

    public static DesiredCapabilities getLocalCapabilitiesForAndroid(){
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(UDID, ConfigurationReader.get().udid());
        capabilities.setCapability(AVD, ConfigurationReader.get().localDeviceName());
        capabilities.setCapability(PLATFORM_NAME, ConfigurationReader.get().platformName());
        capabilities.setCapability(PLATFORM_VERSION, ConfigurationReader.get().platformVersion());
        capabilities.setCapability(BROWSER_NAME, ConfigurationReader.get().browserName());
        capabilities.setCapability("automationName", "UiAutomator2");

        return capabilities;
    }

    public static DesiredCapabilities getLocalCapabilitiesForIOS(){
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(UDID, ConfigurationReader.get().udid());
        capabilities.setCapability(AVD, ConfigurationReader.get().localDeviceName());
        capabilities.setCapability(PLATFORM_NAME, ConfigurationReader.get().platformName());
        capabilities.setCapability(PLATFORM_VERSION, ConfigurationReader.get().platformVersion());
        capabilities.setCapability(BROWSER_NAME, ConfigurationReader.get().browserName());
        return capabilities;
    }

    public static DesiredCapabilities getLocalCapabilitiesForBrowserStack(){
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(PLATFORM_NAME, ConfigurationReader.get().browserstackPlatformName());
        capabilities.setCapability(PLATFORM_VERSION, ConfigurationReader.get().browserstackPlatformVersion());
        capabilities.setCapability(DEVICE_NAME, ConfigurationReader.get().browserstackDeviceName());
        capabilities.setCapability("browserstack.user", ConfigurationReader.get().browserstackUser());
        capabilities.setCapability("browserstack.key", ConfigurationReader.get().browserstackKey());
        return capabilities;
    }
}
