package com.abl.rtbc.model.simplifier;

import lombok.Getter;

import java.util.List;

@Getter
public class Operator extends EquationElement{

    private final int precedence;

    private static final List<String> tier3 = List.of("^");
    private static final List<String> tier2 = List.of("*", "/", "%");
    private static final List<String> tier1 = List.of("+", "-");

    public Operator(String value, ElementType type) {
        super(value, type);
        precedence = calculatePrecedence();
    }

    public Operand operate(Operand lhs, Operand rhs) {
        long result = 0;

        result = switch (getValue()) {
            case "+" -> lhs.getNumericValue() + rhs.getNumericValue();
            case "-" -> lhs.getNumericValue() - rhs.getNumericValue();
            case "*" -> lhs.getNumericValue() * rhs.getNumericValue();
            case "/" -> lhs.getNumericValue() / rhs.getNumericValue();
            case "^" -> lhs.getNumericValue() ^ rhs.getNumericValue();
            default -> result;
        };

        return new Operand(String.valueOf(result), ElementType.OPERAND);
    }

    private int calculatePrecedence() {
        String operator = getValue();
        if (tier3.contains(operator)){
            return 3;
        }
        else if (tier2.contains(operator)){
            return 2;
        }
        else if (tier1.contains(operator)){
            return 1;
        }

        return 0;
    }
}