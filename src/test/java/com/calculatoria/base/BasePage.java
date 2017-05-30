package com.calculatoria.base;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.qatools.htmlelements.element.HtmlElement;

/**
 * Abstract page. Can contain methods to execute JS, etc.
 */
public abstract class BasePage extends HtmlElement {

    private static int ELEMENT_TIMEOUT_SECONDS = 5;

    protected WebDriverWait wait = new WebDriverWait(driver(), ELEMENT_TIMEOUT_SECONDS);

    protected WebDriver driver() {
        return BaseTest.getDriver();
    }


}
