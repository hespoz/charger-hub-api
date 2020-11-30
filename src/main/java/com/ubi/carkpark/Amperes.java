package com.ubi.carkpark;

public enum Amperes {
    OFF(0), MAX_AMPERES_20(20), MIN_AMPERES_10(10);
    private final int value;

    Amperes(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
