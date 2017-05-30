package com.calculatoria.pages;

import com.calculatoria.base.BasePage;
import com.calculatoria.utils.TestListener;
import org.junit.Assert;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import ru.yandex.qatools.allure.annotations.Step;

import java.util.List;
import java.util.Optional;


public class Calculator extends BasePage {

    @FindBy(name = "exprdisplay")
    WebElement display;

    @FindBy(css = "[title='delete display']")
    WebElement clearButton;

    @FindBy(css = "[title='rounding decimal numbers']")
    WebElement roundingSelect;

    @FindBy(css = ".zabtn")
    List<WebElement> buttons;

    @FindBys({
            @FindBy(css = ".btn2"),
            @FindBy(xpath = "./a[text()='=']")
    })
    WebElement calculateButton;


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

    /**
     * Uses list of strings instead of single string because of buttons in advanced mode,
     * e.g. 'x raised to the power of y' button contains text 'x' and 'y' in different elements,
     * but we can obtain the button by searching for element with 'getText().equals("xy")'
     */
    @Step
    public Calculator type(List<String> buttonsText) {
        for (String buttonText : buttonsText) {
            Optional<WebElement> button = this.buttons.stream().filter(b -> b.getText().equals(buttonText)).findFirst();
            Assert.assertTrue(String.format("Button [%s] not found", buttonText), button.isPresent());
            button.get().click();
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
