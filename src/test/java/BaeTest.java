import driver.DriverManager;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

public class BaeTest {

    @BeforeClass
    public void createSession() {
        DriverManager.getDriver();
    }

    @AfterMethod
    public void resetApp() {
        DriverManager.getDriver().executeScript("mobile: reset");
    }

    @AfterClass
    public void closSession() {
        DriverManager.quitDriver();
        DriverManager.closeAppium();
        DriverManager.closeEmulator();
    }
}
