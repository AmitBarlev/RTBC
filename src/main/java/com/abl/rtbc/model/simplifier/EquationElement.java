package com.abl.rtbc.model.simplifier;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
public class EquationElement {

    private final String value;
    private final ElementType type;
}

