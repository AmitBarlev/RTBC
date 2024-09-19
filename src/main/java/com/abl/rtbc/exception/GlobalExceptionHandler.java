package com.abl.rtbc.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Optional;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = ParsingException.class)
    public ResponseEntity<String> handleParsingError(ParsingException ex) {

        String message = Optional.ofNullable(ex.getUnidentifiedCharacter())
                .map(x -> String.format("Parsing error expression: %s, character: %s, message: %s",
                        ex.getExpression(), ex.getUnidentifiedCharacter(), ex.getMessage()))
                .orElse(String.format("Parsing error expression: %s, message: %s", ex.getExpression(), ex.getMessage()));

        log.error(message);

        return ResponseEntity
                .badRequest()
                .body(message);
    }

    @ExceptionHandler(value = InvalidArithmeticException.class)
    public ResponseEntity<String> handleInvalidArithmeticError(InvalidArithmeticException ex) {
        return ResponseEntity
                .badRequest()
                .body(ex.getMessage());
    }

    @ExceptionHandler(value = UninitializedVariableException.class)
    public ResponseEntity<String> handleUninitializedVariableError(UninitializedVariableException ex) {
        return ResponseEntity
                .badRequest()
                .body(String.format("Variable %s was not initialized", ex.getVariableName()));
    }

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<String> handleUnknownError(RuntimeException ex) {

        log.error("Unknown error, throwable is attached", ex);

        return ResponseEntity
                .internalServerError()
                .body("Unknown error, please contact development team");
    }
}
