package com.feature.gating;

import java.util.Iterator;
import java.util.Map;

import static com.feature.gating.Constants.*;

public class Tokenizer implements Iterator<Token> {
    private int index;
    private Map<String, Operator> operatorMap;
    private String expression;

    public Tokenizer(String input, Map<String, Operator> operatorMap){
        expression = input.trim();
        index = 0;
        this.operatorMap = operatorMap;
    }

    public Token next() {
        Token token = new Token();
        if (index >= expression.length()) {
            return null;
        }

        char ch = expression.charAt(index);
        while (Character.isWhitespace(ch) || index >= expression.length()) {
            ch = expression.charAt(++index);
        }

        if (Character.isDigit(ch) || (ch == '.' && Character.isDigit(nextChar()))) {
            boolean isDecimal = false;
            while (Character.isDigit(ch) || (ch == '.' && Character.isDigit(nextChar()))) {
                if (ch == '.' && Character.isDigit(nextChar())) {
                    isDecimal = true;
                }
                token.append(expression.charAt(index++));
                ch = (index >= expression.length()) ? 0 : expression.charAt(index);
            }
            token.type = isDecimal ? TokenDataType.DOUBLE : TokenDataType.INTEGER;
        } else if (ch == '"') {
            index++;
            ch = expression.charAt(index);
            while (ch != '"') {
                token.append(expression.charAt(index++));
                if (index == expression.length()) {
                    throw new Expression.ExpressionException("Condition contains string constant without closing \". Error at index " + index);
                } else {
                    ch = expression.charAt(index);
                }
            }
            token.type = TokenDataType.STRING;
            index++;
        } else if (Character.isLetter(ch)) {
            while (ch != 0 && !Character.isWhitespace(ch) && ch != ')') {
                token.append(expression.charAt(index++));
                ch = (index == expression.length()) ? 0 : expression.charAt(index);
            }
            token.type = TokenDataType.VARIABLE;
        } else if (Character.isWhitespace(ch)) {
            index++;
            token = next();
        } else if (ch == '(' || ch == ')') {
            if (ch == '(') {
                token.type = TokenDataType.OPEN_PARANTHESES;
            } else {
                token.type = TokenDataType.CLOSE_PARANTHESES;
            }
            token.append(expression.charAt(index));
            index++;
        } else {
            while (!Character.isWhitespace(ch)) {
                token.append(expression.charAt(index++));
                ch = (index == expression.length()) ? 0 : expression.charAt(index);
            }
            if (operatorMap.containsKey(token.value)) {
                token.type = TokenDataType.OPERATOR;
            } else {
                throw new Expression.ExpressionException("Operator " + token.value + " is not supported yet. Check the operators supported");
            }
        }

        if (token.type == null) {
            if (operatorMap.containsKey(token.value)) {
                token.type = TokenDataType.OPERATOR;
            } else {
                throw new Expression.ExpressionException("Operator " + token.value + " is not supported yet. Check the operators supported");
            }
        }

        if (operatorMap.containsKey(token.value)) {
            token.type = TokenDataType.OPERATOR;
        }
        if (token.value.equalsIgnoreCase("true") || token.value.equalsIgnoreCase("false")) {
            token.type = TokenDataType.BOOLEAN;
        }

        return token;
    }
    public boolean hasNext() {
        return index < expression.length();
    }

    public void remove() {
        //Not implemented
        throw new Expression.ExpressionException("Remove token method not implemented!");
    }

    private char nextChar() {
        if (index < (expression.length() - 1)) {
            return expression.charAt(index + 1);
        } else {
            return 0;
        }
    }
}