package com.calculatoria.utils;

import org.junit.Assert;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.AbstractMap;

public enum Operations {

    ADD("+", "+") {
        @Override
        public BigDecimal apply(String firstNumber, String secondNumber) {
            BigDecimal result = new BigDecimal(firstNumber).add(new BigDecimal(secondNumber));
            return stripTrailingZeros(result);
        }
    },

    SUBTRACT("-", "-") {
        @Override
        public BigDecimal apply(String firstNumber, String secondNumber) {
            BigDecimal result = new BigDecimal(firstNumber).subtract(new BigDecimal(secondNumber));
            return stripTrailingZeros(result);
        }
    },
    MULTIPLY("*", "×") {
        @Override
        public BigDecimal apply(String firstNumber, String secondNumber) {
            BigDecimal result = new BigDecimal(firstNumber).multiply(new BigDecimal(secondNumber));
            return stripTrailingZeros(result);
        }
    },
    DIVIDE("/", "÷") {
        @Override
        public BigDecimal apply(String firstNumber, String secondNumber) {
            BigDecimal result = new BigDecimal(firstNumber).divide(
                    new BigDecimal(secondNumber), getRoundingValue(), RoundingMode.HALF_UP);
            return stripTrailingZeros(result);
        }
    },
    SQRT("sqrt", "√") {
        @Override
        public BigDecimal apply(String firstNumber, String secondNumber) {
            BigDecimal firstNumDecimal = new BigDecimal(firstNumber);
            BigDecimal x0 = new BigDecimal("0");
            BigDecimal rootOfFirst = new BigDecimal(Math.sqrt(firstNumDecimal.doubleValue()));
            while (!x0.equals(rootOfFirst)) {
                x0 = rootOfFirst;
                rootOfFirst = firstNumDecimal.divide(x0, getRoundingValue(), RoundingMode.HALF_UP);
                rootOfFirst = rootOfFirst.add(x0);
                rootOfFirst = rootOfFirst.divide(new BigDecimal(2), getRoundingValue(), RoundingMode.HALF_UP);
            }
            return stripTrailingZeros(rootOfFirst);
        }
    },
    POW("^", "xy") {
        //TODO: implement pow calculations not only for integers
        @Override
        public BigDecimal apply(String firstNumber, String secondNumber) {
            return new BigDecimal(firstNumber)
                    .pow(Integer.valueOf(secondNumber))
                    .round(new MathContext(12, RoundingMode.DOWN));
        }
    };

    private AbstractMap.SimpleEntry<String, String> option;

    Operations(String operation, String buttonSign) {
        option = new AbstractMap.SimpleEntry<>(operation, buttonSign);
    }


    private int roundingValue = 0;

    public Operations setRoundingValue(int roundingValue) {
        this.roundingValue = roundingValue;
        return this;
    }

    public int getRoundingValue() {
        return roundingValue;
    }


    public abstract BigDecimal apply(String firstNumber, String secondNumber);


    public String getOperation() {
        return option.getKey();
    }

    public String getButtonSign() {
        return option.getValue();
    }


    public static synchronized Operations operationFromString(String value) {
        Operations result = null;

        if (value != null) {
            for (Operations operation : Operations.values()) {
                if (value.equalsIgnoreCase(operation.getOperation())) {
                    result = operation;
                }
            }
        }
        Assert.assertFalse(String.format("Unsupported operation [%s].", value), null == result);
        return result;
    }

    public static synchronized BigDecimal stripTrailingZeros(BigDecimal value) {
        if (value.doubleValue() % 1 == 0) {
            return value.setScale(0, RoundingMode.UNNECESSARY);
        }
        return value;
    }
}
