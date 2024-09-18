package com.abl.rtbc.model.simplifier;


import lombok.Data;

@Data
public class Variable implements Operand, AlgebraicExpressionElement {

    private final String name;
    private Double numericValue;
    private String nameWithSelfOperator;
    private static final String MINUS = "-";
    private static final String INCREMENT = "++";
    private static final String DECREMENT = "--";

    public Variable(String value) {
        numericValue = 0d;
        nameWithSelfOperator = value;
        name = getNameWithoutSelfOperator(value);
    }


    @Override
    public String getName() {
        return name;
    }

    @Override
    public Double getNumericValue() {
        String value = nameWithSelfOperator;
        nameWithSelfOperator = getNameWithoutSelfOperator(nameWithSelfOperator);

        if (value.startsWith(INCREMENT)){
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
    public void setNumericValue(Double value) {
        numericValue = value;
    }

    @Override
    public ElementType getType() {
        return ElementType.OPERAND;
    }

    @Override
    public String getValue() {
        return nameWithSelfOperator;
    }

    @Override
    public String toString() {
        return name;
    }

    public String getNameWithoutSelfOperator(String value) {

        if (value.startsWith(INCREMENT) || value.startsWith(DECREMENT)){
            return value.substring(2);
        }
        else if (value.endsWith(INCREMENT) || value.endsWith(DECREMENT)){
            return value.substring(0, value.length() - 2);
        }

        return value;
    }
}
