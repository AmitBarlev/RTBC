package com.abl.rtbc.model.organizer;

import com.abl.rtbc.model.simplifier.AssignmentOperator;
import com.abl.rtbc.model.simplifier.Number;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RPNPayloadTest {

    @Test
    public void isEmpty_sanity_true() {
        assertTrue(new RPNPayload().isEmpty());
    }

    @Test
    public void isEmpty_sanity_false() {
        RPNPayload payload = new RPNPayload();
        payload.pushAssignment(List.of(new AssignmentOperator("=")));
        assertFalse(payload.isEmpty());
    }

    @Test
    public void isEmpty_oneExpressionLeft_true() {
        RPNPayload payload = new RPNPayload();
        payload.pushAlgebraicExpression(List.of(new Number("2")));
        assertTrue(payload.isEmpty());
    }

    @Test
    public void pushPopAlgebraicExpression_sanity_lastInFirstOut() {
        RPNPayload payload = new RPNPayload();
        payload.pushAlgebraicExpression(List.of(new Number("2")));
        payload.pushAlgebraicExpression(List.of(new Number("3")));
        payload.pushAlgebraicExpression(List.of(new Number("4")));

        assertEquals("4", payload.popAlgebraicExpression().get(0).getValue());
        assertEquals("3", payload.popAlgebraicExpression().get(0).getValue());
        assertEquals("2", payload.popAlgebraicExpression().get(0).getValue());
    }

    @Test
    public void pushPopAssignment_sanity_lastInFirstOut() {
        RPNPayload payload = new RPNPayload();
        payload.pushAssignment(List.of(new AssignmentOperator("=")));
        payload.pushAssignment(List.of(new AssignmentOperator("+=")));
        payload.pushAssignment(List.of(new AssignmentOperator("*=")));

        assertEquals("*=", payload.popAssignment().getValue());
        assertEquals("+=", payload.popAssignment().getValue());
        assertEquals("=", payload.popAssignment().getValue());
    }
}
