package com.abl.rtbc.exception;

import lombok.Generated;
import lombok.Value;

@Value
@Generated
public class InvalidArithmeticException extends RuntimeException {


    public InvalidArithmeticException(String message) {
        super(message);
    }
}
