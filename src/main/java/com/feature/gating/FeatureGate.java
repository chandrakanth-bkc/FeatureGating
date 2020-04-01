package com.feature.gating;

import java.util.Map;

public class FeatureGate {

    private Expression expression;

    public boolean isAllowed(String conditionalExpression, String feature, Map<String, Object> user) {
        expression = new Expression(conditionalExpression);
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
