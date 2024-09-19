package com.abl.rtbc.service.organizer;

import com.abl.rtbc.model.organizer.RPNPayload;
import com.abl.rtbc.model.simplifier.ElementType;
import com.abl.rtbc.model.simplifier.AlgebraicExpressionElement;
import com.abl.rtbc.model.simplifier.Operator;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

@Service
public class ReversePolishNotationOrganizer {

    public RPNPayload organize(List<AlgebraicExpressionElement> equation) {
        RPNPayload rpnPayload = new RPNPayload();
        for (List<AlgebraicExpressionElement> element : splitByEquals(equation)) {

            if (1 == element.size() && ElementType.ASSIGNMENT == element.get(0).getType()) {
                rpnPayload.pushAssignment(element);
            } else {
                rpnPayload.pushAlgebraicExpression(shuntingYard(element));
            }
        }

        return rpnPayload;
    }

    private List<List<AlgebraicExpressionElement>> splitByEquals(List<AlgebraicExpressionElement> elements) {
        List<List<AlgebraicExpressionElement>> splitByEquals = new ArrayList<>();
        List<AlgebraicExpressionElement> current = new ArrayList<>();
        elements.forEach(element -> handleElement(element, splitByEquals, current));
        splitByEquals.add(current);
        return splitByEquals;
    }

    private void handleElement(AlgebraicExpressionElement element, List<List<AlgebraicExpressionElement>> lists, List<AlgebraicExpressionElement> current) {
        if (ElementType.ASSIGNMENT == element.getType()) {
            lists.add(new ArrayList<>(current));
            lists.add(List.of(element));
            current.clear();
        } else {
            current.add(element);
        }
    }

    private List<AlgebraicExpressionElement> shuntingYard(List<AlgebraicExpressionElement> equationChunk) {
        List<AlgebraicExpressionElement> rpnOrder = new ArrayList<>();
        Stack<AlgebraicExpressionElement> stack = new Stack<>();

        for (AlgebraicExpressionElement element: equationChunk) {
            if (ElementType.OPERAND == element.getType()) { //digit or letter
                rpnOrder.add(element);
            } else if (ElementType.CLOSING_BRACKET == element.getType()) {
                unloadStackUntilOpeningBracket(rpnOrder, stack);
            } else if (ElementType.OPENING_BRACKET == element.getType()) {
                stack.push(element);
            } else if (ElementType.OPERATOR == element.getType()) {
                unloadStackAccordingToPrecedence(element, rpnOrder, stack);
            }
        }

        while (!stack.isEmpty()) {
            if (ElementType.OPENING_BRACKET == stack.peek().getType())
                throw new RuntimeException("Not valid");

            rpnOrder.add(stack.pop());
        }

        return rpnOrder;
    }

    private void unloadStackUntilOpeningBracket(List<AlgebraicExpressionElement> rpnOrder, Stack<AlgebraicExpressionElement> stack) {
        while (!stack.isEmpty() && ElementType.OPENING_BRACKET != stack.peek().getType()) {
            rpnOrder.add(stack.pop());
        }
        stack.pop();
    }

    private void unloadStackAccordingToPrecedence(AlgebraicExpressionElement element, List<AlgebraicExpressionElement> rpnOrder,
                                                  Stack<AlgebraicExpressionElement> stack) {

        while (!stack.isEmpty()) {
            Operator currentOperand = (Operator)element;
            if (ElementType.OPENING_BRACKET == stack.peek().getType()) {
                break;
            }

            Operator previousOperand = (Operator)stack.peek();
            if (currentOperand.getPrecedence() > previousOperand.getPrecedence())
                break;

            rpnOrder.add(stack.pop());
        }
        stack.push(element);
    }
}
