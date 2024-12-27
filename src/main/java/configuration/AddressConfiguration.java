package configuration;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.ServerSocket;
import java.util.Optional;

import static io.appium.java_client.service.local.flags.GeneralServerFlag.LOG_LEVEL;
import static io.appium.java_client.service.local.flags.GeneralServerFlag.SESSION_OVERRIDE;

public class AddressConfiguration {
    private static final Logger LOG = LogManager.getRootLogger();
    private static final String ERROR_LOG_LEVEL = "error";
    private static final String KILL_NODE_COMMAND = "taskkill /F /IM node.exe";
    private static AppiumDriverLocalService appiumDriverLocalService;

    private AddressConfiguration(){}

    public static AppiumDriverLocalService getAppiumDriverLocalService(int port){
        if (appiumDriverLocalService == null){
            startService(port);
        }
        return appiumDriverLocalService;
    }
    public static void startService(int port) {
        makePortIsAvailableIfOccupied(port);
        appiumDriverLocalService = new AppiumServiceBuilder()
                .withIPAddress(ConfigurationReader.get().appiumAddress())
                .usingPort(port)
                .withArgument(SESSION_OVERRIDE)
                .withArgument(LOG_LEVEL, ERROR_LOG_LEVEL)
                .build();
        appiumDriverLocalService.start();
        LOG.info("Appium server started on port: {}", port);
    }

    public static void stopService(){
        Optional.ofNullable(appiumDriverLocalService).ifPresent(service ->{
            service.close();
            LOG.info("Appium server stopped");
        });
    }
    private static void makePortIsAvailableIfOccupied(int port) {
        if (!isPortFree(port)){
            try {
                Runtime.getRuntime().exec(KILL_NODE_COMMAND);
            } catch (Exception e){
                LOG.error("Could not execute runtime command message {}", e.getMessage());
            }
        }
    }
    private static boolean isPortFree(int port) {
        boolean isFree = true;
        try (ServerSocket ignored = new ServerSocket(port)){
            LOG.info("Specified port: {} is available or ready to use", port);
        }catch (Exception e){
            isFree = false;
            LOG.warn("Specified port: {} is occupied by some process, process will be terminated", port);
        }
        return isFree;
    }
}
