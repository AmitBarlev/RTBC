package com.abl.rtbc.model.organizer;

import com.abl.rtbc.model.simplifier.*;
import lombok.Value;

import java.util.List;
import java.util.Stack;


public class RPNPayload {

    private final Stack<List<AlgebraicExpressionElement>> expressions;
    private final Stack<AlgebraicExpressionElement> assignments;

    public RPNPayload() {
        expressions = new Stack<>();
        assignments = new Stack<>();
    }

    public boolean isEmpty() {
        return 1 >= expressions.size() && assignments.isEmpty();
    }


    public void pushAlgebraicExpression(List<AlgebraicExpressionElement> equationChunk) {
        expressions.push(equationChunk);
    }

    public void pushAssignment(List<AlgebraicExpressionElement> assignment){
        assignments.push(assignment.get(0));
    }

    public List<AlgebraicExpressionElement> popAlgebraicExpression() {
        return expressions.pop();
    }

    public AlgebraicExpressionElement popAssignment() {
        return assignments.pop();
    }
}
