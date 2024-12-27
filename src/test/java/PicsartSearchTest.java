import driver.DriverManager;
import io.appium.java_client.AppiumDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class PicsartSearchTest {
    private AppiumDriver driver;

    @BeforeMethod
    public void setUp() {
        driver = DriverManager.getDriver();
    }

    @Test
    public void testChrome() {

    }

    @AfterMethod
    public void tearDown() {
        DriverManager.quitDriver();
    }

}
