package com.calculatoria.utils;

import com.calculatoria.base.BaseTest;
import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import ru.yandex.qatools.allure.annotations.Attachment;

import java.util.Arrays;


public class TestListener implements MethodRule {
    @Override
    public Statement apply(Statement base, FrameworkMethod method, Object target) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                try {
                    base.evaluate();
                } catch (Throwable t) {
                    attachScreenshot();
                    throw t; // rethrow to allow the failure to be reported to JUnit
                }
            }
        };
    }

    @Attachment(value = "Page screenshot", type = "image/png")
    public byte[] attachScreenshot() {
        byte[] screenshotAs = null;
        try {
            screenshotAs = ((TakesScreenshot) BaseTest.getDriver()).getScreenshotAs(OutputType.BYTES);
        } catch (Exception e) {
            fail(e);
        }
        return screenshotAs;
    }

    @Attachment(value = "Unable to save screenshot")
    private String fail(Exception e) {
        return String.format("%s\n%s", e.getMessage(), Arrays.toString(e.getStackTrace()));
    }

    @Attachment(value = "Stacktrace")
    public static String attachStacktrace(String message) {
        return message;
    }

}
