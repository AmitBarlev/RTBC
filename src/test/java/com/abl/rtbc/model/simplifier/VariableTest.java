package com.abl.rtbc.model.simplifier;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VariableTest {

    @Test
    public void getType_sanity_typeIsOperand() {
        assertEquals(ElementType.OPERAND, new Variable("u").getType());
    }

    @Test
    public void getNumericValue_minusPlusPlus_valueIsMinus4() {
        Variable v = new Variable("-++u");
        v.setNumericValue(3d);

        assertEquals(-4d, v.getNumericValue());
    }
    @Test
    public void getNumericValue_preIncrement_valueIs4() {
        Variable v = new Variable("++u");
        v.setNumericValue(3d);

        assertEquals(4d, v.getNumericValue());
    }
    @Test
    public void getNumericValue_postIncrement_valueIs4() {
        Variable v = new Variable("u++");
        v.setNumericValue(3d);

        assertEquals(3d, v.getNumericValue());
        assertEquals(4d, v.getNumericValue());
    }
    @Test
    public void getNumericValue_preIncrement_valueIs2() {
        Variable v = new Variable("--u");
        v.setNumericValue(3d);

        assertEquals(2d, v.getNumericValue());
    }

    @Test
    public void getNumericValue_postIncrement_valueIs2() {
        Variable v = new Variable("u--");
        v.setNumericValue(3d);

        assertEquals(3d, v.getNumericValue());
        assertEquals(2d, v.getNumericValue());
    }

}
