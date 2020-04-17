package com.feature.gating;

import java.util.Map;

public class FeatureGate {

    private Expression expression;

    public FeatureGate() {
        expression = new Expression("");
    }

    public void addNewOperator(String value, Operator operator) {
        expression.addNewOperator(value, operator);
    }

    public boolean isAllowed(String conditionalExpression, String feature, Map<String, Object> user) {
        expression.setExpression(conditionalExpression);
        for(String key : user.keySet()) {
            expression.addNewVariable(key, user.get(key));
        }
        Object response = expression.evaluate();
        if(response instanceof Boolean){
            Boolean val = (Boolean) response;
            return val;
        }
        return false;
    }

}
