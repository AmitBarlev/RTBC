package com.abl.rtbc.model.simplifier;

import lombok.*;

@Data
public class Number implements Operand {

    private String value;
    private Double numericValue;

    public Number(String value) {
        this.value = value;
        numericValue = Double.parseDouble(value);
    }

    @Override
    public String getName() {
        return numericValue.toString();
    }

    @Override
    public ElementType getType() {
        return ElementType.OPERAND;
    }

    @Override
    public String getValue() {
        return value;
    }
}
