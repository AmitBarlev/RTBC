package com.abl.rtbc.service.converter;

import com.abl.rtbc.model.converter.RPNStack;
import com.abl.rtbc.model.simplifier.ElementType;
import com.abl.rtbc.model.simplifier.EquationElement;
import com.abl.rtbc.model.simplifier.Operator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Stack;

@Service
public class ReversePolishNotationConverter {

    public RPNStack convert(List<EquationElement> equation) {
        RPNStack rpnStack = new RPNStack();
        Stack<EquationElement> stack = new Stack<>();

        for (EquationElement element : equation) {

            if (ElementType.OPERAND == element.getType() || ElementType.OPENING_BRACKET == element.getType() ||
                    ElementType.EQUALS == element.getType()) { //digit or letter
                rpnStack.push(element);
            } else if (ElementType.CLOSING_BRACKET == element.getType()) {
                while (!stack.isEmpty() && ElementType.OPENING_BRACKET != stack.peek().getType()) {
                    rpnStack.push(stack.pop());
                }
            } else if (ElementType.OPERATOR == element.getType()) {
                while (!stack.isEmpty()) {
                    Operator currentOperand = (Operator) element;
                    Operator previousOperand = (Operator) stack.peek();
                    if (currentOperand.getPrecedence() <= previousOperand.getPrecedence())
                        break;

                    rpnStack.push(stack.pop());
                }
                stack.push(element);
            }
        }

        Stack<EquationElement> reverse = new Stack<>();
        while (!stack.isEmpty()) {
            if (ElementType.OPENING_BRACKET == stack.peek().getType())
                throw new RuntimeException("Not valid");

            reverse.add(stack.pop());
        }

        while (!reverse.isEmpty())
            rpnStack.push(reverse.pop());

        return rpnStack;
    }
}
