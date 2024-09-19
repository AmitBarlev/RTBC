package com.abl.rtbc.model.simplifier;


import com.abl.rtbc.exception.UninitializedVariableException;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

public class Variable implements Operand, AlgebraicExpressionElement {

    @Getter
    private final String name;
    @Setter
    private Double numericValue;
    private String nameWithSelfOperator;
    private static final String MINUS = "-";
    private static final String INCREMENT = "++";
    private static final String DECREMENT = "--";

    public Variable(String value) {
        nameWithSelfOperator = value;
        name = getNameWithoutSelfOperator(value);
    }

    @Override
    public Double getNumericValue() {
        if (Objects.isNull(numericValue))
            throw new UninitializedVariableException("Variable not initialized", nameWithSelfOperator);

        String value = nameWithSelfOperator;
        nameWithSelfOperator = getNameWithoutSelfOperator(nameWithSelfOperator);

        if (value.startsWith(MINUS + INCREMENT)){
            numericValue += 1;
            return -1 * numericValue;
        }
        else if (value.startsWith(INCREMENT)){
            return numericValue += 1;
        }
        else if (value.startsWith(DECREMENT)){
            return numericValue -= 1;
        }
        else if (value.endsWith(INCREMENT)){
            numericValue += 1;
            return numericValue - 1;
        }
        else if (value.endsWith(DECREMENT)){
            numericValue -= 1;
            return numericValue + 1;
        }

        return numericValue;
    }

    @Override
    public ElementType getType() {
        return ElementType.OPERAND;
    }

    @Override
    public String getValue() {
        return nameWithSelfOperator;
    }

    public String getNameWithoutSelfOperator(String value) {

        if (value.startsWith(MINUS + INCREMENT)){
            return value.substring(3);
        }
        else if (value.startsWith(INCREMENT) || value.startsWith(DECREMENT)){
            return value.substring(2);
        }
        else if (value.endsWith(INCREMENT) || value.endsWith(DECREMENT)){
            return value.substring(0, value.length() - 2);
        }

        return value;
    }
}
