package com.feature.gating;

public class Token{

    public String value = "";
    public TokenDataType type;

    public void append(char c) {
        value += c;
    }
}