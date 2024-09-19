package com.abl.rtbc.exception;

import lombok.Generated;
import lombok.Value;

@Value
@Generated
public class UninitializedVariableException extends RuntimeException {

    String variableName;

    public UninitializedVariableException(String message, String variableName){
        super(message);
        this.variableName = variableName;
    }
}
