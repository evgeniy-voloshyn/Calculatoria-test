package com.calculatoria.base;


import com.calculatoria.driver.DriverType;
import com.calculatoria.utils.TestListener;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ThreadGuard;
import ru.stqa.selenium.factory.WebDriverPool;

public class BaseTest {

    private static ThreadLocal<WebDriverPool> factory = new ThreadLocal<>();
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private static ThreadLocal<DriverType> selectedDriverType = new ThreadLocal<>();

    public static WebDriver getDriver() {
        return ThreadGuard.protect(driver.get());
    }

    @Rule
    public TestListener testListener = new TestListener();

    @BeforeClass
    public static void setUp() {
        factory.set(WebDriverPool.DEFAULT);

        DriverType defaultDriverType = DriverType.CHROME;
        String browser = System.getProperty("browser", defaultDriverType.toString()).toUpperCase();

        DriverType driverType = defaultDriverType;
        try {
            driverType = DriverType.valueOf(browser);
        } catch (IllegalArgumentException ignored) {
            System.err.println("Unknown driver specified, will use default: '" + driverType + "'...");
        }
        selectedDriverType.set(driverType);

        DesiredCapabilities desiredCapabilities = selectedDriverType.get().getDesiredCapabilities();

        driver.set(factory.get().getDriver(desiredCapabilities));

        getDriver().manage().window().maximize();
        getDriver().navigate().to(System.getProperty("base.url", "http://www.calculatoria.com/"));
    }

    @AfterClass
    public static void tearDown() {
        factory.get().dismissAll();
    }
}
