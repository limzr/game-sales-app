package com.example.app.util;

public enum QueryType {
    MORE_THAN_AND_EQUAL(">="),
    MORE_THAN(">"),
    EQUAL("="),
    LESS_THAN("<"),
    LESS_THAN_AND_EQUAL("<=");

    public final String symbol;

    private QueryType(String symbol) {
        this.symbol = symbol;
    }
}
