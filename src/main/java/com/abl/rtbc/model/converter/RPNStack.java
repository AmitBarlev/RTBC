package com.abl.rtbc.model.converter;

import com.abl.rtbc.model.simplifier.ElementType;
import com.abl.rtbc.model.simplifier.EquationElement;
import com.abl.rtbc.model.simplifier.Operand;
import com.abl.rtbc.model.simplifier.Operator;
import lombok.Value;

import java.util.Stack;


public class RPNStack {

    Stack<EquationElement> operands;

    Stack<EquationElement> operators;

    public RPNStack() {
        this.operands = new Stack<>();
        this.operators = new Stack<>();
    }

    public boolean isEmpty() {
        return 1 == operands.size() && operators.isEmpty();
    }

    public Operand popOperand() {
        return (Operand)operands.pop();
    }

    public Operator popOperator() {
        return (Operator)operators.pop();
    }

    public void push(EquationElement element) {
        ElementType type = element.getType();
        if (ElementType.OPERAND == type) {
            operands.push(element);
        }
        else if (ElementType.OPERATOR == type || ElementType.EQUALS == type) {
            operators.push(element);
        }
    }
}
