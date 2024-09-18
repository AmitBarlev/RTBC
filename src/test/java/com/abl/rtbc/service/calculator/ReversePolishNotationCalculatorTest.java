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
}
