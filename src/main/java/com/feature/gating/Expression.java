package com.feature.gating;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import static com.feature.gating.Constants.*;

/**
 * Handle a string conditional expression to evaluate to a boolean value
 */
public class Expression {

    private String originalExpression;
    private Map<String, Object> variableMap = new HashMap<String, Object>();

    public Expression(String expression) {
        this.originalExpression = expression;
    }

    public void setExpression(String expression) {
        this.originalExpression = expression;
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
    public List<Token> convertInfixToPostfix(Map<String, Operator> operatorMap) {
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

    public Object evaluate(Map<String, Operator> operatorMap) {
        Stack<Object> stack = new Stack<Object>();
        List<Token> postfixExpression = convertInfixToPostfix(operatorMap);
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
