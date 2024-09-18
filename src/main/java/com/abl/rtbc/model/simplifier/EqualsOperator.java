package com.abl.rtbc.model.simplifier;

public class EqualsOperator extends Operator {
    public EqualsOperator(String value) {
        super(value);
    }

    public EquationElement operate(Operand lhs, Operand rhs) {

        Variable variable = (Variable)lhs;

        switch (getOperator()) {
            case "+=" -> variable.setNumericValue(variable.getNumericValue() + rhs.getNumericValue());
            case "-=" -> variable.setNumericValue(variable.getNumericValue() - rhs.getNumericValue());
            case "*=" -> variable.setNumericValue(variable.getNumericValue() * rhs.getNumericValue());
            case "/=" -> variable.setNumericValue(variable.getNumericValue() / rhs.getNumericValue());
            case "=" -> variable.setNumericValue(rhs.getNumericValue());
        };

        return variable;
    }

    @Override
    public String getValue() {
        return getOperator();
    }
}
