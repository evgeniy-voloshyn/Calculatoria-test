package com.calculatoria.driver;

import org.openqa.selenium.remote.DesiredCapabilities;

import java.nio.file.Paths;


public enum DriverType implements DriverSetup {

    CHROME {
        public DesiredCapabilities getDesiredCapabilities() {
            System.setProperty("webdriver.chrome.driver", Paths.get("src/test/resources/drivers/chromedriver.exe").toString());
            return DesiredCapabilities.chrome();
        }
    },

}