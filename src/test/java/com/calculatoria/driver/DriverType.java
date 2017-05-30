package com.calculatoria.driver;

import org.openqa.selenium.remote.DesiredCapabilities;


public enum DriverType implements DriverSetup {

    CHROME {
        public DesiredCapabilities getDesiredCapabilities() {
            System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/chromedriver.exe");
            return DesiredCapabilities.chrome();
        }
    },

}