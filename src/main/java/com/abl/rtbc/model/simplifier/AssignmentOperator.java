package com.abl.rtbc.model.simplifier;

import lombok.ToString;

public class AssignmentOperator extends Operator {

    private static final String PLUS = "+=";
    private static final String MINUS = "-=";
    private static final String MULTIPLY = "*=";
    private static final String DIVIDE = "/=";
    private static final String ASSIGNMENT = "=";

    public AssignmentOperator(String value) {
        super(value);
    }

    public AlgebraicExpressionElement operate(Operand lhs, Operand rhs) {

        Variable variable = (Variable)lhs;

        switch (getOperator()) {
            case PLUS -> variable.setNumericValue(variable.getNumericValue() + rhs.getNumericValue());
            case MINUS -> variable.setNumericValue(variable.getNumericValue() - rhs.getNumericValue());
            case MULTIPLY -> variable.setNumericValue(variable.getNumericValue() * rhs.getNumericValue());
            case DIVIDE -> variable.setNumericValue(variable.getNumericValue() / rhs.getNumericValue());
            case ASSIGNMENT -> variable.setNumericValue(rhs.getNumericValue());
        };

        return variable;
    }

    @Override
    public ElementType getType() {
        return ElementType.ASSIGNMENT;
    }
}
