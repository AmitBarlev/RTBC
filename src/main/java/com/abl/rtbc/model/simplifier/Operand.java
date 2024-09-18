package com.abl.rtbc.model.simplifier;

public interface Operand extends EquationElement {

    Double getNumericValue();

    void setNumericValue(Double value);

    String getName();
}
