package com.feature.gating;

import static com.feature.gating.Constants.*;

public class Token{

    public String value = "";
    public TokenDataType type;

    public void append(char c) {
        value += c;
    }
}