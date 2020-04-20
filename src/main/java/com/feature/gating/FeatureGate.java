package com.feature.gating;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.feature.gating.Constants.*;

public class FeatureGate {

    private Expression expression;
    private Map<String, Operator> operatorMap = new HashMap<String, Operator>();

    public FeatureGate() {
        expression = new Expression("");

        operatorMap.put("==", new Operator(EQUALITY_PRECEDENCE) {
            @Override
            public Object compute(Object o1, Object o2) {
                if (o1 instanceof Number && o2 instanceof Number) {
                    return o1 == o2;
                } else if (o1 instanceof String && o2 instanceof String) {
                    return o1.toString().equals(o2.toString());
                } else if (o1 instanceof Boolean && o2 instanceof Boolean) {
                    return o1 == o2;
                } else {
                    throw new Expression.ExpressionException("== operator cannot be used on the used data types");
                }
            }
        });

        operatorMap.put("!=", new Operator(EQUALITY_PRECEDENCE) {
            @Override
            public Object compute(Object o1, Object o2) {
                if (o1 instanceof Number && o2 instanceof Number) {
                    return o1 != o2;
                } else if (o1 instanceof String && o2 instanceof String) {
                    return !o1.toString().equals(o2.toString());
                } else if (o1 instanceof Boolean && o2 instanceof Boolean) {
                    return o1 != o2;
                } else {
                    throw new Expression.ExpressionException("!= operator cannot be used on the used data types");
                }
            }
        });

        operatorMap.put(">", new Operator(COMPARATORS_PRECEDENCE) {
            @Override
            public Object compute(Object o1, Object o2) {
                if (o1 instanceof Integer && o2 instanceof Integer) {
                    Integer x = (Integer) o1;
                    Integer y = (Integer) o2;
                    return x > y;
                } else if (o1 instanceof Double && o2 instanceof Double) {
                    Double x = (Double) o1;
                    Double y = (Double) o2;
                    return x > y;
                } else if (o1 instanceof Integer && o2 instanceof Double) {
                    Integer x = (Integer) o1;
                    Double y = (Double) o2;
                    return x > y;
                } else if (o1 instanceof Double && o2 instanceof Integer) {
                    Double x = (Double) o1;
                    Integer y = (Integer) o2;
                    return x > y;
                } else if (o1 instanceof String && o2 instanceof String) {
                    return o1.toString().compareTo(o2.toString()) > 0;
                } else {
                    throw new Expression.ExpressionException("> operator cannot be used on the used data types");
                }
            }
        });

        operatorMap.put(">=", new Operator(COMPARATORS_PRECEDENCE) {
            @Override
            public Object compute(Object o1, Object o2) {
                if (o1 instanceof Integer && o2 instanceof Integer) {
                    Integer x = (Integer) o1;
                    Integer y = (Integer) o2;
                    return x >= y;
                } else if (o1 instanceof Double && o2 instanceof Double) {
                    Double x = (Double) o1;
                    Double y = (Double) o2;
                    return x >= y;
                } else if (o1 instanceof Integer && o2 instanceof Double) {
                    Integer x = (Integer) o1;
                    Double y = (Double) o2;
                    return x >= y;
                } else if (o1 instanceof Double && o2 instanceof Integer) {
                    Double x = (Double) o1;
                    Integer y = (Integer) o2;
                    return x >= y;
                } else if (o1 instanceof String && o2 instanceof String) {
                    return o1.toString().compareTo(o2.toString()) >= 0;
                } else {
                    throw new Expression.ExpressionException(">= operator cannot be used on the used data types");
                }
            }
        });

        operatorMap.put("<", new Operator(COMPARATORS_PRECEDENCE) {
            @Override
            public Object compute(Object o1, Object o2) {
                if (o1 instanceof Integer && o2 instanceof Integer) {
                    Integer x = (Integer) o1;
                    Integer y = (Integer) o2;
                    return x < y;
                } else if (o1 instanceof Double && o2 instanceof Double) {
                    Double x = (Double) o1;
                    Double y = (Double) o2;
                    return x < y;
                } else if (o1 instanceof Integer && o2 instanceof Double) {
                    Integer x = (Integer) o1;
                    Double y = (Double) o2;
                    return x < y;
                } else if (o1 instanceof Double && o2 instanceof Integer) {
                    Double x = (Double) o1;
                    Integer y = (Integer) o2;
                    return x < y;
                } else if (o1 instanceof String && o2 instanceof String) {
                    return o1.toString().compareTo(o2.toString()) < 0;
                } else {
                    throw new Expression.ExpressionException("< operator cannot be used on the used data types");
                }
            }
        });

        operatorMap.put("<=", new Operator(COMPARATORS_PRECEDENCE) {
            @Override
            public Object compute(Object o1, Object o2) {
                if (o1 instanceof Integer && o2 instanceof Integer) {
                    Integer x = (Integer) o1;
                    Integer y = (Integer) o2;
                    return x <= y;
                } else if (o1 instanceof Double && o2 instanceof Double) {
                    Double x = (Double) o1;
                    Double y = (Double) o2;
                    return x <= y;
                } else if (o1 instanceof Integer && o2 instanceof Double) {
                    Integer x = (Integer) o1;
                    Double y = (Double) o2;
                    return x <= y;
                } else if (o1 instanceof Double && o2 instanceof Integer) {
                    Double x = (Double) o1;
                    Integer y = (Integer) o2;
                    return x <= y;
                } else if (o1 instanceof String && o2 instanceof String) {
                    return o1.toString().compareTo(o2.toString()) <= 0;
                } else {
                    throw new Expression.ExpressionException("<= operator cannot be used on the used data types");
                }
            }
        });

        operatorMap.put("OR", new Operator(OR_PRECEDENCE) {
            @Override
            public Object compute(Object o1, Object o2) {
                if (o1 instanceof Boolean && o2 instanceof Boolean) {
                    Boolean x = (Boolean) o1;
                    Boolean y = (Boolean) o2;
                    return x || y;
                } else {
                    throw new Expression.ExpressionException("OR operator cannot be used on the used data types");
                }
            }
        });

        operatorMap.put("AND", new Operator(AND_PRECEDENCE) {
            @Override
            public Object compute(Object o1, Object o2) {
                if (o1 instanceof Boolean && o2 instanceof Boolean) {
                    Boolean x = (Boolean) o1;
                    Boolean y = (Boolean) o2;
                    return x && y;
                } else {
                    throw new Expression.ExpressionException("AND operator cannot be used on the used data types");
                }
            }
        });

        operatorMap.put("ALLOF", new Operator(EQUALITY_PRECEDENCE) {
            @Override
            public Object compute(Object o1, Object o2) {
                if (o1 instanceof List && o2 instanceof List) {
                    List<Object> x = (List<Object>) o1;
                    List<Object> y = (List<Object>) o2;
                    for (Object leftElem : x) {
                        Boolean foundSame = false;
                        for (Object rightElem : y) {
                            if (equals(leftElem, rightElem)) {
                                foundSame = true;
                                break;
                            }
                        }
                        if (!foundSame)
                            return false;
                    }
                    return true;
                } else {
                    throw new Expression.ExpressionException("ALLOF operator cannot be used on non list types");
                }

            }
        });

        operatorMap.put("NONEOF", new Operator(EQUALITY_PRECEDENCE) {
            @Override
            public Object compute(Object o1, Object o2) {
                if (o1 instanceof List && o2 instanceof List) {
                    List<Object> x = (List<Object>) o1;
                    List<Object> y = (List<Object>) o2;
                    for (Object leftElem : x) {
                        for (Object rightElem : y) {
                            if (equals(leftElem, rightElem)) {
                                return false;
                            }
                        }
                    }
                    return true;
                } else {
                    throw new Expression.ExpressionException("NONEOF operator cannot be used on non list types");
                }

            }
        });

        operatorMap.put("BETWEEN", new Operator(COMPARATORS_PRECEDENCE) {
            @Override
            public Object compute(Object o1, Object o2) {
                throw new Expression.ExpressionException("BETWEEN couldn't be converted to >= and <= between two limits. Error!");
            }
        });
    }

    public void addNewOperator(String value, Operator operator) {
        operatorMap.put(value, operator);
    }

    public boolean isAllowed(String conditionalExpression, String feature, Map<String, Object> user) {
        expression.setExpression(conditionalExpression);
        for(String key : user.keySet()) {
            expression.addNewVariable(key, user.get(key));
        }
        Object response = expression.evaluate(operatorMap);
        if(response instanceof Boolean){
            Boolean val = (Boolean) response;
            return val;
        }
        return false;
    }

}
