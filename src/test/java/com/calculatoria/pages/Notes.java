package com.calculatoria.pages;

import com.calculatoria.base.BasePage;
import com.calculatoria.utils.TestListener;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import ru.yandex.qatools.allure.annotations.Step;

import java.util.Arrays;
import java.util.List;


public class Notes extends BasePage {

    @FindBy(css = "#paska")
    WebElement notesTextarea;

    @Step
    public List<String> getNotes() {
        String value = notesTextarea.getAttribute("value");
        return Arrays.asList(value.split("\\n"));
    }

    @Step
    public boolean isNotePresent(String expectedNote) {
        try {
            wait.until((ExpectedCondition<Boolean>) input -> getNotes().contains(expectedNote));
            return true;
        } catch (WebDriverException e) {
            TestListener.attachStacktrace(e.getMessage());
            return false;
        }
    }

}
