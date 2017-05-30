package com.calculatoria.pages;

import com.calculatoria.base.BasePage;
import com.calculatoria.utils.TestListener;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import ru.yandex.qatools.allure.annotations.Step;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;


public class Calculator extends BasePage {

    @FindBy(name = "exprdisplay")
    WebElement display;

    @FindBy(css = "[title='delete display']")
    WebElement clearButton;

    @FindBy(css = "[title='rounding decimal numbers']")
    WebElement roundingSelect;

    @FindAll({
            @FindBy(css = ".abtn0"),
            @FindBy(css = ".abtn1"),
            @FindBy(css = ".abtn3p")
    })
    List<WebElement> buttons;

    @FindBys({
            @FindBy(css = ".btn2"),
            @FindBy(xpath = "./a[text()='=']")
    })
    WebElement calculateButton;

    private String numberButtonPattern = "//a[contains(@class, 'zabtn') and text()='%s']";

    @Step
    public Calculator clearDisplay() {
        clearButton.click();
        try {
            wait.until((ExpectedCondition<Boolean>) input -> display.getAttribute("value").equals("0"));
        } catch (WebDriverException e) {
            TestListener.attachStacktrace(e.getMessage());
            Assert.fail("Filed to clear display");
        }
        return this;
    }


    @Step
    public Calculator typeOperation(String value) {
        Optional<WebElement> button = this.buttons.stream().filter(b -> b.getText().equals(value)).findFirst();
        Assert.assertTrue(String.format("Button [%s] not found", value), button.isPresent());
        button.get().click();
        return this;
    }

    @Step
    public Calculator typeNumber(String value) {
        String regex = "(?!^)";
        for (String number : value.split(regex)) {
            try {
                this.findElement(By.xpath(String.format(numberButtonPattern, number))).click();
            } catch (WebDriverException e) {
                TestListener.attachStacktrace(e.getMessage());
                Assert.fail(String.format("Number buttons with text [%s] not found", number));
            }
        }
        return this;
    }

    @Step
    public Calculator clickCalculate() {
        calculateButton.click();
        return this;
    }

    @Step
    public String getDisplayedValue() {
        return display.getAttribute("value");
    }

    @Step
    public int getRoundingValue() {
        WebElement selectedOption = new Select(roundingSelect).getFirstSelectedOption();
        String text = selectedOption.getText();
        Assert.assertFalse("Filed to get rounding value", text.isEmpty());
        return Integer.valueOf(text);
    }


}
