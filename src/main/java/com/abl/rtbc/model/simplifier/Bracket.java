package com.abl.rtbc.model.simplifier;

import lombok.Getter;

@Getter
public class Bracket implements EquationElement {

    private final ElementType type;
    private final String value;

    public Bracket(ElementType type, String value) {
        this.type = type;
        this.value = value;
    }

}
