package com.feature.gating;

import java.util.*;

/**
 * Handle a string conditional expression to evaluate to a boolean value
 */
public class Expression {

    private String originalExpression;

    private Map<String, Operator> operatorMap = new HashMap<String, Operator>();
    private Map<String, Object> variableMap = new HashMap<String, Object>();

    /**
     * Operator precedence taken from
     * @see <a href="https://introcs.cs.princeton.edu/java/11precedence/"></a>
     */
    public static final int OR_PRECEDENCE = 1;

    public static final int AND_PRECEDENCE = 2;

    public static final int EQUALITY_PRECEDENCE = 3;

    public static final int COMPARATORS_PRECEDENCE = 4;

    public Expression(String expression) {
        this.originalExpression = expression;

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
                    throw new ExpressionException("== operator cannot be used on the used data types");
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
                    throw new ExpressionException("!= operator cannot be used on the used data types");
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
                    throw new ExpressionException("> operator cannot be used on the used data types");
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
                    throw new ExpressionException(">= operator cannot be used on the used data types");
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
                    throw new ExpressionException("< operator cannot be used on the used data types");
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
                    throw new ExpressionException("<= operator cannot be used on the used data types");
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
                    throw new ExpressionException("OR operator cannot be used on the used data types");
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
                    throw new ExpressionException("AND operator cannot be used on the used data types");
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
                    throw new ExpressionException("ALLOF operator cannot be used on non list types");
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
                    throw new ExpressionException("NONEOF operator cannot be used on non list types");
                }

            }
        });

        operatorMap.put("BETWEEN", new Operator(COMPARATORS_PRECEDENCE) {
            @Override
            public Object compute(Object o1, Object o2) {
                throw new ExpressionException("BETWEEN couldn't be converted to >= and <= between two limits. Error!");
            }
        });
    }

    public void setExpression(String expression) {
        this.originalExpression = expression;
    }

    public void addNewVariable(String variable, Object value) {
        variableMap.put(variable, value);
    }

    public void addNewOperator(String value, Operator operator) {
        operatorMap.put(value, operator);
    }
    //extend RunTimeException
    public static class ExpressionException extends RuntimeException {
        public ExpressionException(String message) {
            super(message);
        }
    }

    public List<Token> handleBETWEEN(List<Token> infixExpression) {
        Token current;
        Token prev = null;
        List<Token> result = new ArrayList<Token>();
        Iterator<Token> tokenIterator = infixExpression.iterator();
        try {
            while (tokenIterator.hasNext()) {
                current = tokenIterator.next();
                if (current.type == TokenDataType.OPERATOR && current.value.equalsIgnoreCase("BETWEEN")) {
                    //change (a BETWEEN b c) to (a >= b AND a <= c)

                    Token greaterThanEqual = new Token();
                    greaterThanEqual.value = ">=";
                    greaterThanEqual.type = TokenDataType.OPERATOR;
                    result.add(greaterThanEqual);

                    // second operand
                    current = tokenIterator.next();
                    result.add(current);

                    // AND operator
                    Token andOperator = new Token();
                    andOperator.value = "AND";
                    andOperator.type = TokenDataType.OPERATOR;
                    result.add(andOperator);

                    // add first operand back for upper limit
                    result.add(prev);

                    // add <= operator
                    Token lessThanEqual = new Token();
                    lessThanEqual.value = "<=";
                    lessThanEqual.type = TokenDataType.OPERATOR;
                    result.add(lessThanEqual);

                    // third operand
                    current = tokenIterator.next();
                    result.add(current);
                }
                else {
                    result.add(current);
                }
                prev = current;
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ExpressionException("BETWEEN operator could not be converted to two comparison operators and an AND operator");
        }
    }

    /**
     * convert infix notation (a op b) to postfix notation (a b op) for easier evaluation
     * Ref - https://www.geeksforgeeks.org/expression-evaluation/
     */
    public List<Token> convertInfixToPostfix() {
        List<Token> infix = new ArrayList<Token>();
        List<Token> postFix = new ArrayList<Token>();
        Stack<Token> stack = new Stack<Token>();
        Iterator<Token> tokenIterator = new Tokenizer(originalExpression, operatorMap);
        while(tokenIterator.hasNext()) {
            infix.add(tokenIterator.next());
        }
        infix = handleBETWEEN(infix);
        tokenIterator = infix.iterator();
        try {
            while (tokenIterator.hasNext()) {
                Token token = tokenIterator.next();
                if (token.type == TokenDataType.VARIABLE || token.type == TokenDataType.BOOLEAN || token.type == TokenDataType.STRING || token.type == TokenDataType.INTEGER || token.type == TokenDataType.DOUBLE) {
                    postFix.add(token);
                } else if (token.type == TokenDataType.OPEN_PARANTHESES) {
                    stack.push(token);
                } else if (token.type == TokenDataType.CLOSE_PARANTHESES) {
                    while (!stack.isEmpty() && !stack.peek().value.equalsIgnoreCase("(")) {
                        postFix.add(stack.pop());
                    }
                    if (!stack.isEmpty() && stack.peek().value.equalsIgnoreCase("(")) {
                        stack.pop();
                    }
                } else {
                    while (!stack.isEmpty() && !stack.peek().value.equalsIgnoreCase("(") && operatorMap.get(token.value).getPrecedence() <= operatorMap.get(stack.peek().value).getPrecedence()) {
                        postFix.add(stack.pop());
                    }
                    stack.push(token);
                }
            }
            while (!stack.isEmpty()) {
                postFix.add(stack.pop());
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ExpressionException("Error! Couldn't evaluate expression");
        }
        return postFix;
    }

    public Object evaluate() {
        Stack<Object> stack = new Stack<Object>();
        List<Token> postfixExpression = convertInfixToPostfix();
        for(Token token : postfixExpression) {
            if(token.type == TokenDataType.VARIABLE) {
                stack.push(variableMap.get(token.value));
            }
            else if(token.type == TokenDataType.BOOLEAN) {
                stack.push(token.value.equalsIgnoreCase("true") ? true : false);
            }
            else if(token.type == TokenDataType.DOUBLE) {
                stack.push(Double.parseDouble(token.value));
            }
            else if(token.type == TokenDataType.INTEGER) {
                stack.push(Integer.parseInt(token.value));
            }
            else if(token.type == TokenDataType.OPERATOR) {
                Operator operator = operatorMap.get(token.value);
                Object operand2 = stack.pop();
                Object operand1 = stack.pop();
                stack.push(operator.compute(operand1, operand2));
            }
            else {
                stack.push(token.value);
            }
        }
        //should be true or false
        if(stack.size() != 1) {
            throw new ExpressionException("Error in expression. Couldn't evaluate to a boolean value");
        }
        return stack.pop();
    }
}
