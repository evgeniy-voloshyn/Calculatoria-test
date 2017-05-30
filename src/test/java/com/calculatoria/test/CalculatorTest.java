package com.calculatoria.test;


import com.calculatoria.base.BaseTest;
import com.calculatoria.pages.Calculator;
import com.calculatoria.pages.MainPage;
import com.calculatoria.utils.CalculatorType;
import com.calculatoria.utils.NumberFormatter;
import com.calculatoria.utils.Operations;
import com.calculatoria.utils.readers.TestDataPoolReader;
import com.calculatoria.utils.readers.TestDataPoolRecord;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@RunWith(DataProviderRunner.class)
public class CalculatorTest extends BaseTest {


    @DataProvider
    public static Object[][] loadFromExternalFile() {
        List<TestDataPoolRecord> records = TestDataPoolReader.INSTANCE.getRecords();

        Object[][] arrayPoolData;
        arrayPoolData = new String[records.size()][4];

        for (int i = 0; i < records.size(); i++) {
            arrayPoolData[i][0] = records.get(i).getFirstNumber();
            arrayPoolData[i][1] = records.get(i).getSecondNumber();
            arrayPoolData[i][2] = records.get(i).getOperation();
            arrayPoolData[i][3] = records.get(i).getCalculatorType();
        }

        return arrayPoolData;
    }

    @Test
    @UseDataProvider("loadFromExternalFile")
    public void testCalculator(String firstNumber, String secondNumber, String operation, String calculatorType) {
        MainPage mainPage = new MainPage();

        Calculator calculator = mainPage
                .selectCalculatorType(CalculatorType.fromTitle(calculatorType))
                .calculator();

        Operations action = Operations.operationFromString(operation);
        String formattedFirst = NumberFormatter.toCalculatorFormat(firstNumber);
        String formattedSecond = NumberFormatter.formatNegativeNumber(secondNumber);
        formattedSecond =  NumberFormatter.toCalculatorFormat(formattedSecond);

        calculator
                .clearDisplay()
                .typeNumber(formattedFirst)
                .typeOperation(action.getButtonSign());

        //skip second number if it is empty, e.g. for sqrt
        if (!secondNumber.isEmpty()){
            calculator.typeNumber(formattedSecond);
        }

        calculator
                .clickCalculate();


        int roundingValue = calculator.getRoundingValue();
        String result = action
                .setRoundingValue(roundingValue)
                .apply(firstNumber, secondNumber)
                .toString();

        String expectedDisplayValue = NumberFormatter.formatDisplayedValue(result);
        String displayedValue = calculator.getDisplayedValue();

        Assert.assertEquals("Wrong calculated value displayed", expectedDisplayValue, displayedValue);


        String expectedNote = NumberFormatter.toNotesFormat(firstNumber, secondNumber, operation, result);
        boolean notePresent = mainPage
                .notes()
                .isNotePresent(expectedNote);

        Assert.assertTrue(String.format("Note [%s] is not present.", expectedNote), notePresent);
    }
}
