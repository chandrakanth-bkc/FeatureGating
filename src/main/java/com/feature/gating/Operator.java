package com.feature.gating;

/**
 * Operator class for handing the operators mentioned in the exercise
 * Assuming binary operators only
 */
public abstract class Operator {
    private int precedence;

    public Operator(int precedence){
        this.precedence = precedence;
    }

    public int getPrecedence(){
        return precedence;
    }

    public abstract Object compute(Object o1, Object o2);

    public Boolean equals(Object o1, Object o2) {
        if(o1 instanceof Boolean && o2 instanceof Boolean) {
            Boolean x = (Boolean) o1;
            Boolean y = (Boolean) o2;
            return x == y;
        }
        else if(o1 instanceof String && o2 instanceof String) {
            String x = (String) o1;
            String y = (String) o2;
            return x.equals(y);
        }
        else if(o1 instanceof Number && o2 instanceof Number) {
            Number x = (Number) o1;
            Number y = (Number) o2;
            return x == y;
        }
        else {
            return false;
        }
    }
}
