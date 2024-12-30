package configuration;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import lombok.extern.log4j.Log4j2;

import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.URL;
import java.util.Optional;

import static io.appium.java_client.service.local.flags.GeneralServerFlag.LOG_LEVEL;
import static io.appium.java_client.service.local.flags.GeneralServerFlag.SESSION_OVERRIDE;
@Log4j2
public class AddressConfiguration {
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
        log.info("Appium server started on port: {}", port);
    }

    public static void stopService(){
        Optional.ofNullable(appiumDriverLocalService).ifPresent(service ->{
            service.close();
            log.info("Appium server stopped");
        });
    }
    private static void makePortIsAvailableIfOccupied(int port) {
        if (!isPortFree(port)){
            try {
                Runtime.getRuntime().exec(KILL_NODE_COMMAND);
            } catch (Exception e){
                log.error("Could not execute runtime command message {}", e.getMessage());
            }
        }
    }
    private static boolean isPortFree(int port) {
        boolean isFree = true;
        try (ServerSocket ignored = new ServerSocket(port)){
            log.info("Specified port: {} is available or ready to use", port);
        }catch (Exception e){
            isFree = false;
            log.warn("Specified port: {} is occupied by some process, process will be terminated", port);
        }
        return isFree;
    }
    public static URL getURL(String address){
        URL url ;
        try {
            url = new URL(address);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        return url;
    }
}
