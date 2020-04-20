package com.feature.gating;

public final class Constants {
    /**
     * Operator precedence taken from
     * @see <a href="https://introcs.cs.princeton.edu/java/11precedence/"></a>
     */
    public static final int OR_PRECEDENCE = 1;

    public static final int AND_PRECEDENCE = 2;

    public static final int EQUALITY_PRECEDENCE = 3;

    public static final int COMPARATORS_PRECEDENCE = 4;

    public enum TokenDataType {
        OPERATOR,
        VARIABLE,
        INTEGER,
        DOUBLE,
        STRING,
        BOOLEAN,
        OPEN_PARANTHESES,
        CLOSE_PARANTHESES
    }
}
