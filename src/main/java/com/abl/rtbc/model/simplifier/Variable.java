package com.abl.rtbc.model.simplifier;


import lombok.Data;

@Data
public class Variable implements Operand, EquationElement {

    private final String name;
    private Double numericValue;
    private String nameWithSelfOperator;
    private static final String increment = "++";
    private static final String decrement = "--";

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

        if (value.startsWith(increment)){
            return numericValue += 1;
        }
        else if (value.startsWith(decrement)){
            return numericValue -= 1;
        }
        else if (value.endsWith(increment)){
            numericValue += 1;
            return numericValue - 1;
        }
        else if (value.endsWith(decrement)){
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

    public String getNameWithoutSelfOperator(String value) {

        if (value.startsWith(increment) || value.startsWith(decrement)){
            return value.substring(2);
        }
        else if (value.endsWith(increment) || value.endsWith(decrement)){
            return value.substring(0, value.length() - 2);
        }

        return value;
    }
}
