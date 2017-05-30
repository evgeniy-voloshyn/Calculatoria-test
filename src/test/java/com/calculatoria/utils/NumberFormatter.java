package com.calculatoria.utils;


import java.math.BigDecimal;

public class NumberFormatter {

    private static String calculatorDot = "·";
    private static String dot = ".";
    private static String comma = ",";

    private NumberFormatter() {
    }

    public static synchronized String toCalculatorFormat(String value) {
        return value.replace(dot, calculatorDot).replace(comma, calculatorDot);
    }

    public static synchronized String toNotesFormat(String firstNumber, String secondNumber, String operation, String result) {
        secondNumber = formatNegativeNumber(secondNumber);

        String value = firstNumber + operation + secondNumber + "=" + result;

        //if operation is sqrt - note is in format '&radic; firstNumber=result'
        if (Operations.operationFromString(operation).equals(Operations.SQRT)) {
            value = "&radic; " + firstNumber + "=" + result;
        }
        return value.replace(comma, dot).toLowerCase();
    }

    public static synchronized String formatNegativeNumber(String value) {
        if (value.isEmpty()) {
            return value;
        }

        //if number is negative surround it with parentheses
        if (new BigDecimal(value).signum() == -1) {
            value = "(" + value + ")";
        }
        return value;
    }

    public static synchronized String formatDisplayedValue(String value) {
        //if length of calculated value is more than 18, remove beginning of the value
        if (value.length() > 18) {
            return value.substring(value.length() - 18);
        }

        return value.toLowerCase();
    }
}
