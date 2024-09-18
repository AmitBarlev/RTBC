package com.abl.rtbc.service.calculator;

import com.abl.rtbc.model.CalculatorResponse;
import com.abl.rtbc.model.converter.RPNPayload;
import com.abl.rtbc.model.simplifier.Number;
import com.abl.rtbc.model.simplifier.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReversePolishNotationCalculatorTest {

    private final ReversePolishNotationCalculator calculator = new ReversePolishNotationCalculator();

    @Test
    public void calculate_simpleAssignment_iIs0() {

        RPNPayload rpnPayload = new RPNPayload();
        rpnPayload.pushAssignment(List.of(new AssignmentOperator("=")));
        rpnPayload.pushAlgebraicExpression(List.of(new Variable("i")));
        rpnPayload.pushAlgebraicExpression(List.of(new Number("0")));

        CalculatorResponse response = calculator.calculate(List.of(rpnPayload));

        assertEquals(0d,response.getVariables().get("i"));
    }

    @Test
    public void calculate_simpleAssignmentAndPlusOperator_iIs11() {
        RPNPayload rpnPayload = new RPNPayload();

        rpnPayload.pushAlgebraicExpression(List.of(new Variable("i")));
        rpnPayload.pushAlgebraicExpression(List.of(new Number("4"), new Number("7"), new Operator("+")));
        rpnPayload.pushAssignment(List.of(new AssignmentOperator("=")));

        CalculatorResponse response = calculator.calculate(List.of(rpnPayload));
        assertEquals(11d, response.getVariables().get("i"));
    }

    @Test
    public void calculate_simpleAssignmentAndPlusOperatorAndSubtractOperator_iIs10() {
        RPNPayload rpnPayload = new RPNPayload();

        List<AlgebraicExpressionElement> rhs = List.of(new Number("4"),
                new Number("7"),
                new Operator("+"),
                new Number("1"),
                new Operator("-"));
        rpnPayload.pushAlgebraicExpression(List.of(new Variable("i")));
        rpnPayload.pushAlgebraicExpression(rhs);
        rpnPayload.pushAssignment(List.of(new AssignmentOperator("=")));

        CalculatorResponse response = calculator.calculate(List.of(rpnPayload));
        assertEquals(10d, response.getVariables().get("i"));
    }

    @Test
    public void calculate_MultipleLinesMultiplyAssignment_iIs70() {
        RPNPayload rpnPayload = new RPNPayload();
        rpnPayload.pushAssignment(List.of(new AssignmentOperator("=")));
        rpnPayload.pushAlgebraicExpression(List.of(new Variable("y")));
        rpnPayload.pushAlgebraicExpression(List.of(new Number("2")));

        RPNPayload rpnPayload2 = new RPNPayload();

        List<AlgebraicExpressionElement> rhs = List.of(
                new Number("5"),
                new Number("3"),
                new Number("10"),
                new Operator("*"),
                new Operator("+")
        );
        rpnPayload2.pushAlgebraicExpression(List.of(new Variable("y")));
        rpnPayload2.pushAlgebraicExpression(rhs);
        rpnPayload2.pushAssignment(List.of(new AssignmentOperator("*=")));

        CalculatorResponse response = calculator.calculate(List.of(rpnPayload, rpnPayload2));
        assertEquals(70d, response.getVariables().get("y"));
    }

    @Test
    public void calculate_complicatedExpression_i3Is95() {
        RPNPayload rpnPayload = new RPNPayload();
        rpnPayload.pushAssignment(List.of(new AssignmentOperator("=")));
        rpnPayload.pushAlgebraicExpression(List.of(new Variable("i3")));
        rpnPayload.pushAlgebraicExpression(List.of(new Number("2")));

        RPNPayload rpnPayload2 = new RPNPayload();
        rpnPayload2.pushAssignment(List.of(new AssignmentOperator("=")));
        rpnPayload2.pushAlgebraicExpression(List.of(new Variable("q47")));
        rpnPayload2.pushAlgebraicExpression(List.of(
                new Number("2"),
                new Number("4"),
                new Variable("-++i3"),
                new Number("3"),
                new Operator("-"),
                new Operator("*"),
                new Number("9"),
                new Number("12"),
                new Number("1"),
                new Operator("+"),
                new Operator("*"),
                new Operator("+"),
                new Operator("+")
        ));

        CalculatorResponse response = calculator.calculate(List.of(rpnPayload, rpnPayload2));
        assertEquals(3d, response.getVariables().get("i3"));
        assertEquals(95d, response.getVariables().get("q47"));
    }

    @Test
    public void calculate_multipleLinesMultipleAssignments_i3Is95() {
        RPNPayload rpnPayload = new RPNPayload();
        rpnPayload.pushAssignment(List.of(new AssignmentOperator("=")));
        rpnPayload.pushAlgebraicExpression(List.of(new Variable("i1")));
        rpnPayload.pushAlgebraicExpression(List.of(new Number("1")));

        RPNPayload rpnPayload2 = new RPNPayload();
        rpnPayload2.pushAssignment(List.of(new AssignmentOperator("=")));
        rpnPayload2.pushAlgebraicExpression(List.of(new Variable("i2")));
        rpnPayload2.pushAlgebraicExpression(List.of(new Number("2")));

        RPNPayload rpnPayload3 = new RPNPayload();
        rpnPayload3.pushAssignment(List.of(new AssignmentOperator("=")));
        rpnPayload3.pushAlgebraicExpression(List.of(new Variable("i3")));
        rpnPayload3.pushAlgebraicExpression(List.of(new Number("3")));

        RPNPayload rpnFinal = new RPNPayload();
        rpnFinal.pushAssignment(List.of(new AssignmentOperator("-=")));
        rpnFinal.pushAssignment(List.of(new AssignmentOperator("+=")));
        rpnFinal.pushAlgebraicExpression(List.of(new Variable("i3")));
        rpnFinal.pushAlgebraicExpression(List.of(new Variable("i2")));
        rpnFinal.pushAlgebraicExpression(List.of(new Variable("i1")));


        CalculatorResponse response = calculator.calculate(List.of(rpnPayload, rpnPayload2,
                rpnPayload3, rpnFinal));

        assertEquals(1d, response.getVariables().get("i1"));
        assertEquals(3d, response.getVariables().get("i2"));
        assertEquals(0d, response.getVariables().get("i3"));
    }

    @Test
    public void calculate_singleDecrement_i3Is95() {
        RPNPayload rpnPayload = new RPNPayload();
        rpnPayload.pushAssignment(List.of(new AssignmentOperator("=")));
        rpnPayload.pushAlgebraicExpression(List.of(new Variable("i2")));
        rpnPayload.pushAlgebraicExpression(List.of(new Number("1")));

        RPNPayload rpnPayload2 = new RPNPayload();
        rpnPayload2.pushAlgebraicExpression(List.of(new Variable("i2++")));

        CalculatorResponse response = calculator.calculate(List.of(rpnPayload, rpnPayload2));

        assertEquals(2d, response.getVariables().get("i2"));
    }
}
