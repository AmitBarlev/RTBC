package com.abl.rtbc.model.simplifier;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NumberTest {

    @Test
    public void getType_sanity_typeIsOperand() {
        assertEquals(ElementType.OPERAND, new Number("1").getType());
    }

    @Test
    public void getName_number_equalsStrings() {
        assertEquals("1.0", new Number("1").getName());
    }

    @Test
    public void getValue_number_equalsStrings() {
        assertEquals("-1", new Number("-1").getValue());
    }
}
