package com.abl.rtbc.service.organizer;

import com.abl.rtbc.model.organizer.RPNPayload;
import com.abl.rtbc.model.simplifier.*;
import com.abl.rtbc.model.simplifier.Number;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReversePolishNotationOrganizerTest {

    private final ReversePolishNotationOrganizer converter = new ReversePolishNotationOrganizer();


    @Test
    public void convert_simpleAssignment_payloadInTheRightOrder() {

        List<AlgebraicExpressionElement> list = List.of(
                new Variable("i"),
                new AssignmentOperator("="),
                new Number("0")
        );

        RPNPayload rpnPayload = converter.organize(list);

        List<AlgebraicExpressionElement> output = rpnPayload.popAlgebraicExpression();
        assertEquals(1, output.size());
        assertEquals("0", output.get(0).getValue());

        output = rpnPayload.popAlgebraicExpression();
        assertEquals(1, output.size());
        assertEquals("i", output.get(0).getValue());

        AlgebraicExpressionElement assignment = rpnPayload.popAssignment();
        assertEquals(ElementType.ASSIGNMENT, assignment.getType());
        assertEquals("=", assignment.getValue());
    }

    @Test
    public void convert_simpleAssignmentAndPlusOperator_payloadInTheRightOrder() {

        List<AlgebraicExpressionElement> list = List.of(
                new Variable("i"),
                new AssignmentOperator("="),
                new Number("4"),
                new Operator("+"),
                new Number("7")
        );

        RPNPayload rpnPayload = converter.organize(list);

        List<AlgebraicExpressionElement> output = rpnPayload.popAlgebraicExpression();
        assertEquals(3, output.size());
        assertEquals("4", output.get(0).getValue());
        assertEquals("7", output.get(1).getValue());
        assertEquals("+", output.get(2).getValue());

        output = rpnPayload.popAlgebraicExpression();
        assertEquals(1, output.size());
        assertEquals("i", output.get(0).getValue());

        AlgebraicExpressionElement assignment = rpnPayload.popAssignment();
        assertEquals(ElementType.ASSIGNMENT, assignment.getType());
        assertEquals("=", assignment.getValue());
    }

    @Test
    public void convert_simpleAssignmentAndPlusOperatorAndSubtractOperator_payloadInTheRightOrder() {
        List<AlgebraicExpressionElement> list = List.of(
                new Variable("i"),
                new AssignmentOperator("+="),
                new Number("4"),
                new Operator("+"),
                new Number("7"),
                new Operator("-"),
                new Number("1")
        );

        RPNPayload rpnPayload = converter.organize(list);

        List<AlgebraicExpressionElement> output = rpnPayload.popAlgebraicExpression();
        assertEquals(5, output.size());
        assertEquals("4", output.get(0).getValue());
        assertEquals("7", output.get(1).getValue());
        assertEquals("+", output.get(2).getValue());
        assertEquals("1", output.get(3).getValue());
        assertEquals("-", output.get(4).getValue());


        output = rpnPayload.popAlgebraicExpression();
        assertEquals(1, output.size());
        assertEquals("i", output.get(0).getValue());

        AlgebraicExpressionElement assignment = rpnPayload.popAssignment();
        assertEquals(ElementType.ASSIGNMENT, assignment.getType());
        assertEquals("+=", assignment.getValue());

    }

    @Test
    public void convert_multiplyAssignment_stackInTheRightOrder() {
        //y = 5 + 3 * 10
        List<AlgebraicExpressionElement> list = List.of(
                new Variable("y"),
                new AssignmentOperator("*="),
                new Number("5"),
                new Operator("+"),
                new Number("3"),
                new Operator("*"),
                new Number("10")
        );

        RPNPayload rpnPayload = converter.organize(list);

        List<AlgebraicExpressionElement> output = rpnPayload.popAlgebraicExpression();
        assertEquals(5, output.size());
        assertEquals("5", output.get(0).getValue());
        assertEquals("3", output.get(1).getValue());
        assertEquals("10", output.get(2).getValue());
        assertEquals("*", output.get(3).getValue());
        assertEquals("+", output.get(4).getValue());

        output = rpnPayload.popAlgebraicExpression();
        assertEquals(1, output.size());
        assertEquals("y", output.get(0).getValue());

        AlgebraicExpressionElement assignment = rpnPayload.popAssignment();
        assertEquals(ElementType.ASSIGNMENT, assignment.getType());
        assertEquals("*=", assignment.getValue());
    }

    @Test
    public void convert_simpleAssignmentWithBrackets_stackInTheRightOrder() {
        List<AlgebraicExpressionElement> list = List.of(
                new Variable("j"),
                new AssignmentOperator("="),
                new Bracket(ElementType.OPENING_BRACKET),
                new Number("5"),
                new Operator("+"),
                new Number("3"),
                new Bracket(ElementType.CLOSING_BRACKET),
                new Operator("*"),
                new Number("3")
        );

        RPNPayload rpnPayload = converter.organize(list);

        List<AlgebraicExpressionElement> output = rpnPayload.popAlgebraicExpression();
        assertEquals(5, output.size());
        assertEquals("5", output.get(0).getValue());
        assertEquals("3", output.get(1).getValue());
        assertEquals("+", output.get(2).getValue());
        assertEquals("3", output.get(3).getValue());
        assertEquals("*", output.get(4).getValue());

        output = rpnPayload.popAlgebraicExpression();
        assertEquals(1, output.size());
        assertEquals("j", output.get(0).getValue());

        AlgebraicExpressionElement assignment = rpnPayload.popAssignment();
        assertEquals(ElementType.ASSIGNMENT, assignment.getType());
        assertEquals("=", assignment.getValue());
    }

    @Test
    public void convert_multipleBrackets_stackInTheRightOrder() {
        //i3 = 2 + (4 * (-3 - 3) + (9 * (12 + 1)))
        List<AlgebraicExpressionElement> list = List.of(
                new Variable("i3"),
                new AssignmentOperator("="),
                new Number("2"),
                new Operator("+"),
                new Bracket(ElementType.OPENING_BRACKET),
                new Number("4"),
                new Operator("*"),
                new Bracket(ElementType.OPENING_BRACKET),
                new Variable("-++i"),
                new Operator("-"),
                new Number("3"),
                new Bracket(ElementType.CLOSING_BRACKET),
                new Operator("+"),
                new Bracket(ElementType.OPENING_BRACKET),
                new Number("9"),
                new Operator("*"),
                new Bracket(ElementType.OPENING_BRACKET),
                new Number("12"),
                new Operator("+"),
                new Number("1"),
                new Bracket(ElementType.CLOSING_BRACKET),
                new Bracket(ElementType.CLOSING_BRACKET),
                new Bracket(ElementType.CLOSING_BRACKET)
        );

        RPNPayload rpnPayload = converter.organize(list);

        List<AlgebraicExpressionElement> output = rpnPayload.popAlgebraicExpression();
        assertEquals(13, output.size());
        assertEquals("2", output.get(0).getValue());
        assertEquals("4", output.get(1).getValue());
        assertEquals("-++i", output.get(2).getValue());
        assertEquals("3", output.get(3).getValue());
        assertEquals("-", output.get(4).getValue());
        assertEquals("*", output.get(5).getValue());
        assertEquals("9", output.get(6).getValue());
        assertEquals("12", output.get(7).getValue());
        assertEquals("1", output.get(8).getValue());
        assertEquals("+", output.get(9).getValue());
        assertEquals("*", output.get(10).getValue());
        assertEquals("+", output.get(11).getValue());
        assertEquals("+", output.get(12).getValue());


        output = rpnPayload.popAlgebraicExpression();
        assertEquals(1, output.size());
        assertEquals("i3", output.get(0).getValue());

        AlgebraicExpressionElement assignment = rpnPayload.popAssignment();
        assertEquals(ElementType.ASSIGNMENT, assignment.getType());
        assertEquals("=", assignment.getValue());
    }

    @Test
    public void convert_multipleAssignments_stackInTheRightOrder() {
        //i3 = i2 += i1 -= 40
        List<AlgebraicExpressionElement> list = List.of(
                new Variable("i3"),
                new AssignmentOperator("="),
                new Variable("i2"),
                new AssignmentOperator("+="),
                new Variable("i1"),
                new AssignmentOperator("-="),
                new Number("40")
        );

        RPNPayload rpnPayload = converter.organize(list);

        List<AlgebraicExpressionElement> output = rpnPayload.popAlgebraicExpression();
        assertEquals(1, output.size());
        assertEquals("40", output.get(0).getValue());

        output = rpnPayload.popAlgebraicExpression();
        assertEquals(1, output.size());
        assertEquals("i1", output.get(0).getValue());

        AlgebraicExpressionElement assignment = rpnPayload.popAssignment();
        assertEquals(ElementType.ASSIGNMENT, assignment.getType());
        assertEquals("-=", assignment.getValue());

        output = rpnPayload.popAlgebraicExpression();
        assertEquals(1, output.size());
        assertEquals("i2", output.get(0).getValue());

        assignment = rpnPayload.popAssignment();
        assertEquals(ElementType.ASSIGNMENT, assignment.getType());
        assertEquals("+=", assignment.getValue());

        output = rpnPayload.popAlgebraicExpression();
        assertEquals(1, output.size());
        assertEquals("i3", output.get(0).getValue());

        assignment = rpnPayload.popAssignment();
        assertEquals(ElementType.ASSIGNMENT, assignment.getType());
        assertEquals("=", assignment.getValue());
    }

    @Test
    public void convert_singleDecrement_stackInTheRightOrder() {
        //i3 = i2 += i1 -= 40
        List<AlgebraicExpressionElement> list = List.of(
                new Variable("i--")
        );

        RPNPayload rpnPayload = converter.organize(list);

        List<AlgebraicExpressionElement> output = rpnPayload.popAlgebraicExpression();
        assertEquals(1, output.size());
        assertEquals("i--", output.get(0).getValue());
    }
}
