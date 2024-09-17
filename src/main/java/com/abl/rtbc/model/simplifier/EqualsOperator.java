package com.abl.rtbc.model.simplifier;

public class EqualsOperator extends Operator {
    public EqualsOperator(String value, ElementType type) {
        super(value, type);
    }

    public Operand operate(Operand lhs, Operand rhs) {

        Variable variable = (Variable)lhs;

        switch (getValue()) {
            case "+=" -> variable.setNumericValue(variable.getNumericValue() + rhs.getNumericValue());
            case "-=" -> variable.setNumericValue(variable.getNumericValue() - rhs.getNumericValue());
            case "*=" -> variable.setNumericValue(variable.getNumericValue() * rhs.getNumericValue());
            case "/=" -> variable.setNumericValue(variable.getNumericValue() / rhs.getNumericValue());
            case "^=" -> variable.setNumericValue(variable.getNumericValue() ^ rhs.getNumericValue());
            case "=" -> variable.setNumericValue(rhs.getNumericValue());
        };

        return variable;
    }
}
