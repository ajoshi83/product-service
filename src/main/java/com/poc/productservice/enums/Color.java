package com.poc.productservice.enums;

import java.util.Arrays;

public enum Color {
    RED("Red"),
    GREEN("Green"),
    BLUE("Blue"),
    YELLOW("Yellow"),
    BLACK("Black"),
    SAFFRON("Saffron"),
    INDIGO("Indigo"),
    PURPLE("Purple"),
    TAN("Tan");
    private String colorValue;

    Color(final String colorValue) {
        this.colorValue=colorValue;
    }

    public static Color fromColorString(final String colourName) {
        return Arrays.stream(Color.values())
                .filter(clr -> clr.colorValue.equals(colourName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid Color"));
    }


}
