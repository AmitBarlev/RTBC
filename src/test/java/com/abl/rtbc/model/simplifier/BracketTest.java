package com.abl.rtbc.model.simplifier;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BracketTest {

    @Test
    public void openingBracket_sanity_expectedValue() {
        Bracket b = new Bracket(ElementType.OPENING_BRACKET);
        assertEquals("(", b.getValue());
    }

    @Test
    public void closingBracket_sanity_expectedValue() {
        Bracket b = new Bracket(ElementType.CLOSING_BRACKET);
        assertEquals(")", b.getValue());
    }
}
