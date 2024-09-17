package com.abl.rtbc.model.simplifier;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Operand extends EquationElement{

    protected Long numericValue;
    private static final String increment = "++";
    private static final String decrement = "--";

    public Operand(String value, ElementType type, boolean isVariable) {
        super(value, type);
        numericValue = isVariable ? null : Long.parseLong(value);
    }

    public Operand(String value, ElementType type) {
        this(value, type, false);
    }

    public long value() {
        String value = getValue();
        if (value.startsWith(increment)){
            return ++numericValue;
        }
        else if (value.startsWith(decrement)){
            return --numericValue;
        }
        else if (value.endsWith(increment)){
            return numericValue++;
        }
        else if (value.endsWith(decrement)){
            return numericValue--;
        }

        return numericValue;
    }
}
