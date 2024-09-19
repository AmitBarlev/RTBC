package com.abl.rtbc.model.simplifier;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OperatorTest {

    @Test
    public void getType_sanity_typeIsOperator() {
        assertEquals(ElementType.OPERATOR, new Operator("+").getType());
    }

    @Test
    public void operate_plus_resultIs6() {
        Operator operator = new Operator("+");
        Number output = (Number)operator.operate(new Number("3"), new Number("3"));
        assertEquals(6d, output.getNumericValue());
        assertEquals("6.0", output.getName());
    }

    @Test
    public void operate_minus_variableIs0() {
        Operator operator = new Operator("-");
        Number output = (Number)operator.operate(new Number("3"), new Number("3"));
        assertEquals(0d, output.getNumericValue());
        assertEquals("0.0", output.getName());
    }

    @Test
    public void operate_multiply_variableIs9() {
        Operator operator = new Operator("*");
        Number output = (Number)operator.operate(new Number("3"), new Number("3"));
        assertEquals(9d, output.getNumericValue());
        assertEquals("9.0", output.getName());
    }

    @Test
    public void operate_divide_variableIs1() {
        Operator operator = new Operator("/");
        Number output = (Number)operator.operate(new Number("3"), new Number("3"));
        assertEquals(1d, output.getNumericValue());
        assertEquals("1.0", output.getName());
    }
}
