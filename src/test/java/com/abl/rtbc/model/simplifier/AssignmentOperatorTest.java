package com.abl.rtbc.model.simplifier;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AssignmentOperatorTest {

    @Test
    public void getType_assignmentOperator_assignmentOperator() {
        assertEquals(ElementType.ASSIGNMENT, new AssignmentOperator("=").getType());
    }

    @Test
    public void operate_assignmentOperator_variableIs3() {
        AssignmentOperator operator = new AssignmentOperator("=");
        Variable variable = new Variable("i");
        variable.setNumericValue(0d);;
        Variable output = (Variable)operator.operate(variable, new Number("3"));
        assertEquals(3d, output.getNumericValue());
        assertEquals("i", output.getName());
    }

    @Test
    public void operate_plusAssignmentOperator_variableIs4() {
        AssignmentOperator operator = new AssignmentOperator("+=");
        Variable variable = new Variable("i");
        variable.setNumericValue(1d);
        Variable output = (Variable)operator.operate(variable, new Number("3"));
        assertEquals(4d, output.getNumericValue());
        assertEquals("i", output.getName());
    }

    @Test
    public void operate_minusAssignmentOperator_variableIsMinus2() {
        AssignmentOperator operator = new AssignmentOperator("-=");
        Variable variable = new Variable("i");
        variable.setNumericValue(1d);
        Variable output = (Variable)operator.operate(variable, new Number("3"));
        assertEquals(-2d, output.getNumericValue());
        assertEquals("i", output.getName());
    }

    @Test
    public void operate_multiplyAssignmentOperator_variableIsMinus6() {
        AssignmentOperator operator = new AssignmentOperator("*=");
        Variable variable = new Variable("i");
        variable.setNumericValue(2d);
        Variable output = (Variable)operator.operate(variable, new Number("3"));
        assertEquals(6d, output.getNumericValue());
        assertEquals("i", output.getName());
    }

    @Test
    public void operate_divideAssignmentOperator_variableIs1AndHalf() {
        AssignmentOperator operator = new AssignmentOperator("/=");
        Variable variable = new Variable("i");
        variable.setNumericValue(3d);
        Variable output = (Variable)operator.operate(variable, new Number("2"));
        assertEquals(1.5d, output.getNumericValue());
        assertEquals("i", output.getName());
    }
}
