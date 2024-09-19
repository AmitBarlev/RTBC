package com.abl.rtbc.exception;

import lombok.EqualsAndHashCode;
import lombok.Generated;
import lombok.Getter;

@Getter
@Generated
@EqualsAndHashCode(callSuper = true)
public class ParsingException extends RuntimeException{

    private Character unidentifiedCharacter;
    private final String expression;

    public ParsingException(String message, String expression, Character unidentifiedCharacter){
        super(message);
        this.unidentifiedCharacter = unidentifiedCharacter;
        this.expression = expression;
    }

    public ParsingException(String message, String expression){
        super(message);
        this.expression = expression;
    }
}

