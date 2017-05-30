package com.calculatoria.utils;

import org.junit.Assert;


public enum CalculatorType {

    BASIC("basic"),
    ADVANCED("advanced");

    private String value;

    CalculatorType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static CalculatorType fromString(String value) {
        if (value != null) {
            for (CalculatorType type : CalculatorType.values()) {
                if (value.equalsIgnoreCase(type.getValue())) {
                    return type;
                }
            }
        }
        Assert.fail(String.format("Unsupported calculator type [%s]. Use one of [%s]", value, Operations.values()[0]));
        return null;
    }
}
