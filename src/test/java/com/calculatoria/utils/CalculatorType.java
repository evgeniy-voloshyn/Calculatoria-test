package com.calculatoria.utils;

import org.junit.Assert;

import java.util.AbstractMap;


public enum CalculatorType {

    BASIC("basic", "standard calculator"),
    ADVANCED("advanced", "scientific calculator");

    private AbstractMap.SimpleEntry<String, String> option;
    CalculatorType(String title, String value) {option = new AbstractMap.SimpleEntry<>(title, value);
    }

    public String getTitle() {
        return option.getKey();
    }

    public String getValue() {
        return option.getValue();
    }

    public static CalculatorType fromTitle(String title) {
        if (title != null) {
            for (CalculatorType type : CalculatorType.values()) {
                if (title.equalsIgnoreCase(type.getTitle())) {
                    return type;
                }
            }
        }
        Assert.fail(String.format("Unsupported calculator type [%s]. Use one of [%s]", title, Operations.values()[0]));
        return null;
    }
}
