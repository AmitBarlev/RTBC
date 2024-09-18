package com.abl.rtbc.model.simplifier;

public interface Operand extends AlgebraicExpressionElement {

    Double getNumericValue();

    void setNumericValue(Double value);

    String getName();
}
