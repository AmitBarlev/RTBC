package com.abl.rtbc.model.simplifier;

import lombok.Getter;

import java.util.List;

@Getter
public class Operator implements EquationElement {

    private final int precedence;
    private final String operator;

    private static final List<String> tier2 = List.of("*", "/", "%");
    private static final List<String> tier1 = List.of("+", "-");

    public Operator(String value) {
        operator = value;
        precedence = calculatePrecedence();
    }

    public EquationElement operate(Operand lhs, Operand rhs) {
        Double result = 0d;

        result = switch (operator) {
            case "+" -> lhs.getNumericValue() + rhs.getNumericValue();
            case "-" -> lhs.getNumericValue() - rhs.getNumericValue();
            case "*" -> lhs.getNumericValue() * rhs.getNumericValue();
            case "/" -> lhs.getNumericValue() / rhs.getNumericValue();
            default -> result;
        };

        return new Number(String.valueOf(result));
    }

    @Override
    public ElementType getType() {
        return ElementType.OPERATOR;
    }

    @Override
    public String getValue() {
        return getOperator();
    }

    private int calculatePrecedence() {
        String operator = getOperator();
        if (tier2.contains(operator)){
            return 2;
        }
        else if (tier1.contains(operator)){
            return 1;
        }

        return 0;
    }
}