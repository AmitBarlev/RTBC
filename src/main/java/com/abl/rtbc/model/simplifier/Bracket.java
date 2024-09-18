package com.abl.rtbc.model.simplifier;

import lombok.Getter;

@Getter
public class Bracket implements AlgebraicExpressionElement {

    private final ElementType type;
    private final String value;

    public Bracket(ElementType type) {
        this.type = type;
        this.value = ElementType.OPENING_BRACKET == type ? "(" : ")";
    }

}
