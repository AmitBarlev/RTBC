package com.abl.rtbc.model.simplifier;

import lombok.Getter;

@Getter
public class Variable extends Operand {

    public Variable(String value, ElementType type) {
        super(value, type, true);
    }

}
