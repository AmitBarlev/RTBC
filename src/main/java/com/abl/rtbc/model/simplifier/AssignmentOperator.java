package com.abl.rtbc.model.simplifier;

public class AssignmentOperator extends Operator {
    public AssignmentOperator(String value) {
        super(value);
    }

    public AlgebraicExpressionElement operate(Operand lhs, Operand rhs) {

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
    public ElementType getType() {
        return ElementType.ASSIGNMENT;
    }
}
