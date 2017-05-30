package com.calculatoria.pages;

import com.calculatoria.base.BasePage;
import com.calculatoria.utils.CalculatorType;
import com.calculatoria.utils.TestListener;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ru.yandex.qatools.allure.annotations.Step;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementDecorator;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementLocatorFactory;

import javax.annotation.Nullable;


public class MainPage extends BasePage {

    @FindBy(linkText = "basic")
    WebElement basicLink;

    @FindBy(linkText = "advanced")
    WebElement advancedLink;

    @FindBy(id = "advcalcbox")
    WebElement advancedPart;

    @FindBy(id = "ocalc")
    private Calculator calculator;

    @FindBys({
            @FindBy(id = "paskabox"),
            @FindBy(xpath = "..")
    })
    private Notes notes;

    public MainPage() {
        PageFactory.initElements(new HtmlElementDecorator(new HtmlElementLocatorFactory(driver())), this);
    }

    @Step
    public MainPage selectCalculatorType(CalculatorType type) {
        driver().findElement(By.linkText(type.getValue())).click();
        try {
            wait.until((ExpectedCondition<Boolean>) input -> !driver().findElements(By.linkText(type.getTitle())).isEmpty());
        } catch (WebDriverException e){
            TestListener.attachStacktrace(e.getMessage());
            Assert.fail(String.format("Filed to select calculator type: [%s]", type.getValue()));
        }
        return this;
    }

    @Step
    public Calculator calculator() {
        return calculator;
    }

    @Step
    public Notes notes() {
        return notes;
    }
}
