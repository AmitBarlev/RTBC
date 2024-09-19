package com.abl.rtbc.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void handleParsingError_withUnidentifiedCharacter_badRequest() {
        String expression = "2 + 2";
        Character unidentifiedCharacter = '_';
        String message = "message";
        ParsingException parsingException = new ParsingException(message, expression, unidentifiedCharacter);

        ResponseEntity<String> response = handler.handleParsingError(parsingException);

        String expectedMessage = String.format("Parsing error expression: %s, character: %c, message: %s"
                , expression, unidentifiedCharacter, message);
        assertEquals(expectedMessage, response.getBody());
    }

    @Test
    public void handleParsingError_withoutUnidentifiedCharacter_badRequest() {
        String expression = "2 ++ 2";
        String message = "message";
        ParsingException parsingException = new ParsingException(message, expression);

        ResponseEntity<String> response = handler.handleParsingError(parsingException);

        String expectedMessage = String.format("Parsing error expression: %s, message: %s", expression, message);
        assertEquals(expectedMessage, response.getBody());
    }

    @Test
    public void handleInvalidArithmeticError_sanity_badRequest() {
        String message = "Invalid arithmetic operation";
        InvalidArithmeticException invalidArithmeticException = new InvalidArithmeticException(message);

        ResponseEntity<String> response = handler.handleInvalidArithmeticError(invalidArithmeticException);

        assertEquals(message, response.getBody());
    }

    @Test
    public void handleUninitializedVariableError_sanity_badRequest() {
        String variableName = "x";
        UninitializedVariableException uninitializedVariableException = new UninitializedVariableException(
                "Variable not initialized", variableName);

        ResponseEntity<String> response = handler.handleUninitializedVariableError(uninitializedVariableException);

        String expectedMessage = String.format("Variable %s was not initialized", variableName);
        assertEquals(expectedMessage, response.getBody());
    }

    @Test
    public void handlerUnkownError_sanity_internalServerError() {
        RuntimeException ex = new RuntimeException("Unknown error");

        ResponseEntity<String> response = handler.handleUnknownError(ex);

        assertEquals("Unknown error, please contact development team", response.getBody());
    }
}
