package com.abl.rtbc.model.converter;

import com.abl.rtbc.model.simplifier.*;

import java.util.List;
import java.util.Stack;


public class RPNPayload {

    Stack<List<AlgebraicExpressionElement>> equations;
    Stack<AlgebraicExpressionElement> assignments;

    public RPNPayload() {
        equations = new Stack<>();
        assignments = new Stack<>();
    }

    public boolean isEmpty() {
        return 1 >= equations.size() && assignments.isEmpty();
    }


    public void pushAlgebraicExpression(List<AlgebraicExpressionElement> equationChunk) {
        equations.push(equationChunk);
    }

    public void pushAssignment(List<AlgebraicExpressionElement> assignment){
        assignments.push(assignment.get(0));
    }

    public List<AlgebraicExpressionElement> popAlgebraicExpression() {
        return equations.pop();
    }

    public AlgebraicExpressionElement popAssignment() {
        return assignments.pop();
    }
}
