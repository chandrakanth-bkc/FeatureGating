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

    //constants for different tokens in the expression
    public static final String OPERATOR = "OPERATOR";

    public static final String VARIABLE = "VARIABLE";

    public static final String INTEGER = "INTEGER";

    public static final String DOUBLE = "DOUBlE";

    public static final String BOOLEAN = "BOOLEAN";

    public static final String STRING = "STRING";

    public static final String OPEN_PARANTHESES = "(";

    public static final String CLOSE_PARANTHESES = ")";

    public Expression(String expression) {
        this.originalExpression = expression;

        operatorMap.put("==", new Operator(EQUALITY_PRECEDENCE) {
            @Override
            public Object compute(Object o1, Object o2) {
                if(o1 instanceof Number && o2 instanceof Number) {
                    return o1 == o2;
                }
                else if(o1 instanceof String && o2 instanceof String) {
                    return o1.toString().equals(o2.toString());
                }
                else if(o1 instanceof Boolean && o2 instanceof Boolean) {
                    return o1 == o2;
                }
                else {
                    throw new ExpressionException("== operator cannot be used on the used data types");
                }
            }
        });

        operatorMap.put("!=", new Operator(EQUALITY_PRECEDENCE) {
            @Override
            public Object compute(Object o1, Object o2) {
                if(o1 instanceof Number && o2 instanceof Number) {
                    return o1 != o2;
                }
                else if(o1 instanceof String && o2 instanceof String) {
                    return !o1.toString().equals(o2.toString());
                }
                else if(o1 instanceof Boolean && o2 instanceof Boolean) {
                    return o1 != o2;
                }
                else {
                    throw new ExpressionException("!= operator cannot be used on the used data types");
                }
            }
        });

        operatorMap.put(">", new Operator(COMPARATORS_PRECEDENCE) {
            @Override
            public Object compute(Object o1, Object o2) {
                if(o1 instanceof Integer && o2 instanceof Integer) {
                    Integer x = (Integer) o1;
                    Integer y = (Integer) o2;
                    return x > y;
                }
                else if(o1 instanceof Double && o2 instanceof Double) {
                    Double x = (Double) o1;
                    Double y = (Double) o2;
                    return x > y;
                }
                else if(o1 instanceof Integer && o2 instanceof Double) {
                    Integer x = (Integer) o1;
                    Double y = (Double) o2;
                    return x > y;
                }
                else if(o1 instanceof Double && o2 instanceof Integer) {
                    Double x = (Double) o1;
                    Integer y = (Integer) o2;
                    return x > y;
                }
                else if(o1 instanceof String && o2 instanceof String) {
                    return o1.toString().compareTo(o2.toString()) > 0;
                }
                else {
                    throw new ExpressionException("> operator cannot be used on the used data types");
                }
            }
        });

        operatorMap.put(">=", new Operator(COMPARATORS_PRECEDENCE) {
            @Override
            public Object compute(Object o1, Object o2) {
                if(o1 instanceof Integer && o2 instanceof Integer) {
                    Integer x = (Integer) o1;
                    Integer y = (Integer) o2;
                    return x >= y;
                }
                else if(o1 instanceof Double && o2 instanceof Double) {
                    Double x = (Double) o1;
                    Double y = (Double) o2;
                    return x >= y;
                }
                else if(o1 instanceof Integer && o2 instanceof Double) {
                    Integer x = (Integer) o1;
                    Double y = (Double) o2;
                    return x >= y;
                }
                else if(o1 instanceof Double && o2 instanceof Integer) {
                    Double x = (Double) o1;
                    Integer y = (Integer) o2;
                    return x >= y;
                }
                else if(o1 instanceof String && o2 instanceof String) {
                    return o1.toString().compareTo(o2.toString()) >= 0;
                }
                else {
                    throw new ExpressionException(">= operator cannot be used on the used data types");
                }
            }
        });

        operatorMap.put("<", new Operator(COMPARATORS_PRECEDENCE) {
            @Override
            public Object compute(Object o1, Object o2) {
                if(o1 instanceof Integer && o2 instanceof Integer) {
                    Integer x = (Integer) o1;
                    Integer y = (Integer) o2;
                    return x < y;
                }
                else if(o1 instanceof Double && o2 instanceof Double) {
                    Double x = (Double) o1;
                    Double y = (Double) o2;
                    return x < y;
                }
                else if(o1 instanceof Integer && o2 instanceof Double) {
                    Integer x = (Integer) o1;
                    Double y = (Double) o2;
                    return x < y;
                }
                else if(o1 instanceof Double && o2 instanceof Integer) {
                    Double x = (Double) o1;
                    Integer y = (Integer) o2;
                    return x < y;
                }
                else if(o1 instanceof String && o2 instanceof String) {
                    return o1.toString().compareTo(o2.toString()) < 0;
                }
                else {
                    throw new ExpressionException("< operator cannot be used on the used data types");
                }
            }
        });

        operatorMap.put("<=", new Operator(COMPARATORS_PRECEDENCE) {
            @Override
            public Object compute(Object o1, Object o2) {
                if(o1 instanceof Integer && o2 instanceof Integer) {
                    Integer x = (Integer) o1;
                    Integer y = (Integer) o2;
                    return x <= y;
                }
                else if(o1 instanceof Double && o2 instanceof Double) {
                    Double x = (Double) o1;
                    Double y = (Double) o2;
                    return x <= y;
                }
                else if(o1 instanceof Integer && o2 instanceof Double) {
                    Integer x = (Integer) o1;
                    Double y = (Double) o2;
                    return x <= y;
                }
                else if(o1 instanceof Double && o2 instanceof Integer) {
                    Double x = (Double) o1;
                    Integer y = (Integer) o2;
                    return x <= y;
                }
                else if(o1 instanceof String && o2 instanceof String) {
                    return o1.toString().compareTo(o2.toString()) <= 0;
                }
                else {
                    throw new ExpressionException("<= operator cannot be used on the used data types");
                }
            }
        });

        operatorMap.put("OR", new Operator(OR_PRECEDENCE) {
            @Override
            public Object compute(Object o1, Object o2) {
                if(o1 instanceof Boolean && o2 instanceof Boolean) {
                    Boolean x = (Boolean) o1;
                    Boolean y = (Boolean) o2;
                    return x || y;
                }
                else {
                    throw new ExpressionException("OR operator cannot be used on the used data types");
                }
            }
        });

        operatorMap.put("AND", new Operator(AND_PRECEDENCE) {
            @Override
            public Object compute(Object o1, Object o2) {
                if(o1 instanceof Boolean && o2 instanceof Boolean) {
                    Boolean x = (Boolean) o1;
                    Boolean y = (Boolean) o2;
                    return x && y;
                }
                else {
                    throw new ExpressionException("AND operator cannot be used on the used data types");
                }
            }
        });


        operatorMap.put("ALLOF", new Operator(EQUALITY_PRECEDENCE) {
            @Override
            public Object compute(Object o1, Object o2) {
                if(o1 instanceof List && o2 instanceof List) {
                    List<Object> x = (List<Object>) o1;
                    List<Object> y = (List<Object>) o2;
                    for(Object leftElem : x) {
                        Boolean foundSame = false;
                        for(Object rightElem : y) {
                            if(equals(leftElem, rightElem)) {
                                foundSame = true;
                                break;
                            }
                        }
                        if (!foundSame)
                            return false;
                    }
                    return true;
                }
                else {
                    throw new ExpressionException("ALLOF operator cannot be used on non list types");
                }

            }
        });

        operatorMap.put("NONEOF", new Operator(EQUALITY_PRECEDENCE) {
            @Override
            public Object compute(Object o1, Object o2) {
                if(o1 instanceof List && o2 instanceof List) {
                    List<Object> x = (List<Object>) o1;
                    List<Object> y = (List<Object>) o2;
                    for(Object leftElem : x) {
                        for(Object rightElem : y) {
                            if(equals(leftElem, rightElem)) {
                                return false;
                            }
                        }
                    }
                    return true;
                }
                else {
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

    public void addNewVariable(String variable, Object value) {
        variableMap.put(variable, value);
    }

    //extend RunTimeException
    public static class ExpressionException extends RuntimeException {
        public ExpressionException(String message) {
            super(message);
        }
    }

    private class Token {
        public String value = "";
        public String type;

        public void append(char c) {
            value += c;
        }
    }

    private class Tokenizer implements Iterator<Token> {
        private int index = 0;
        private String input;
        private Token previous;

        public Tokenizer(String input) {
            this.input = input.trim();
            index = 0;
        }

        public Token next() {
            Token token = new Token();
            if(index >= input.length()) {
                return previous = null;
            }

            char ch = input.charAt(index);
            while(Character.isWhitespace(ch) || index >= input.length()) {
                ch = input.charAt(++index);
            }

            if(Character.isDigit(ch) || (ch == '.' && Character.isDigit(nextChar()))) {
                boolean isDecimal = false;
                while(Character.isDigit(ch) || (ch == '.' && Character.isDigit(nextChar()))) {
                    if(ch == '.' && Character.isDigit(nextChar())) {
                        isDecimal = true;
                    }
                    token.append(input.charAt(index++));
                    ch = (index >= input.length()) ? 0 : input.charAt(index);
                }
                token.type = isDecimal ? DOUBLE : INTEGER;
            }
            else if(ch == '"') {
                index++;
                ch = input.charAt(index);
                while(ch != '"') {
                    token.append(input.charAt(index++));
                    if(index == input.length()) {
                        throw new ExpressionException("Condition contains string constant without closing \". Error at index "+ index);
                    }
                    else {
                        ch = input.charAt(index);
                    }
                }
                token.type = STRING;
                index++;
            }
            else if(Character.isLetter(ch)) {
                while(ch != 0 && !Character.isWhitespace(ch) && ch != ')') {
                    token.append(input.charAt(index++));
                    ch = (index == input.length()) ? 0 : input.charAt(index);
                }
                token.type = VARIABLE;
            }
            else if(Character.isWhitespace(ch)) {
                index++;
                token = next();
            }
            else if(ch == '(' || ch == ')'){
                if(ch == '(') {
                    token.type = OPEN_PARANTHESES;
                }
                else {
                    token.type = CLOSE_PARANTHESES;
                }
                token.append(input.charAt(index));
                index++;
            }
            else {
                while(!Character.isWhitespace(ch)) {
                    token.append(input.charAt(index++));
                    ch = (index == input.length()) ? 0 : input.charAt(index);
                }
                if(operatorMap.containsKey(token.value)) {
                    token.type = OPERATOR;
                }
                else {
                    throw new ExpressionException("Operator " + token.value + " is not supported yet. Check the operators supported");
                }
            }

            if(token.type == null) {
                if(operatorMap.containsKey(token.value)) {
                    token.type = OPERATOR;
                }
                else {
                    throw new ExpressionException("Operator " + token.value + " is not supported yet. Check the operators supported");
                }
            }

            if(operatorMap.containsKey(token.value)) {
                token.type = OPERATOR;
            }
            if(token.value.equalsIgnoreCase("true") || token.value.equalsIgnoreCase("false")) {
                token.type = BOOLEAN;
            }

            return previous = token;
        }

        public boolean hasNext() {
            return index < input.length();
        }

        public void remove() {
            //Not implemented
            throw new ExpressionException("Remove token method not implemented!");
        }

        private char nextChar() {
            if (index < (input.length() - 1)) {
                return input.charAt(index + 1);
            } else {
                return 0;
            }
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
                if (current.type == OPERATOR && current.value.equalsIgnoreCase("BETWEEN")) {
                    //change (a BETWEEN b c) to (a >= b AND a <= c)

                    Token greaterThanEqual = new Token();
                    greaterThanEqual.value = ">=";
                    greaterThanEqual.type = OPERATOR;
                    result.add(greaterThanEqual);

                    // second operand
                    current = tokenIterator.next();
                    result.add(current);

                    // AND operator
                    Token andOperator = new Token();
                    andOperator.value = "AND";
                    andOperator.type = OPERATOR;
                    result.add(andOperator);

                    // add first operand back for upper limit
                    result.add(prev);

                    // add <= operator
                    Token lessThanEqual = new Token();
                    lessThanEqual.value = "<=";
                    lessThanEqual.type = OPERATOR;
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
        Iterator<Token> tokenIterator = new Tokenizer(originalExpression);
        while(tokenIterator.hasNext()) {
            infix.add(tokenIterator.next());
        }
        infix = handleBETWEEN(infix);
        tokenIterator = infix.iterator();
        try {
            while (tokenIterator.hasNext()) {
                Token token = tokenIterator.next();
                if (token.type == VARIABLE || token.type == BOOLEAN || token.type == STRING || token.type == INTEGER || token.type == DOUBLE) {
                    postFix.add(token);
                } else if (token.type == OPEN_PARANTHESES) {
                    stack.push(token);
                } else if (token.type == CLOSE_PARANTHESES) {
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
            if(token.type == VARIABLE) {
                stack.push(variableMap.get(token.value));
            }
            else if(token.type == BOOLEAN) {
                stack.push(token.value.equalsIgnoreCase("true") ? true : false);
            }
            else if(token.type == DOUBLE) {
                stack.push(Double.parseDouble(token.value));
            }
            else if(token.type == INTEGER) {
                stack.push(Integer.parseInt(token.value));
            }
            else if(token.type == OPERATOR) {
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
