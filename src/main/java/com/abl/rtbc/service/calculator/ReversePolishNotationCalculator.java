package com.abl.rtbc.service.calculator;

import com.abl.rtbc.model.CalculatorResponse;
import com.abl.rtbc.model.organizer.RPNPayload;
import com.abl.rtbc.model.simplifier.*;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service

public class ReversePolishNotationCalculator {

    public CalculatorResponse calculate(List<RPNPayload> algebraicExpressions) {
        Map<String, Operand> variables = new HashMap<>();
        algebraicExpressions.forEach(stack -> calculateLine(stack, variables));
        Map<String, Double> output = variables
                .entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().getNumericValue()));

        return new CalculatorResponse(output);
    }

    private void calculateLine(RPNPayload rpnPayload, Map<String, Operand> variables) {

        while (!rpnPayload.isEmpty()) {

            Operand rhs = calculateEquation(rpnPayload.popAlgebraicExpression(), variables);

            Operand lhs = calculateEquation(rpnPayload.popAlgebraicExpression(), variables);
            Operator assignment = (Operator)rpnPayload.popAssignment();

            assignment.operate(lhs, rhs);
            rpnPayload.pushAlgebraicExpression(List.of(lhs));
            variables.put(lhs.getName(), lhs);
            variables.replace(rhs.getName(), rhs);
        }

        handlePossibleIncrementDecrement(rpnPayload, variables);
    }

    private void handlePossibleIncrementDecrement(RPNPayload payload, Map<String, Operand> variables) {
        Variable variable = (Variable)payload.popAlgebraicExpression().get(0);

        Optional.ofNullable(variables.get(variable.getName()))
                .ifPresent(memorizedRhs -> variable.setNumericValue(memorizedRhs.getNumericValue()));
        variable.getNumericValue(); //if increment/decrement, value will change
        variables.put(variable.getName(), variable);
    }

    private Operand calculateEquation(List<AlgebraicExpressionElement> equation, Map<String, Operand> variables) {
        Stack<AlgebraicExpressionElement> stack = new Stack<>();

        for (AlgebraicExpressionElement element: equation) {
            if (ElementType.OPERATOR == element.getType()) {
                Operator operator = (Operator)element;
                Operand rhs = (Operand)stack.pop();
                Operand lhs = (Operand)stack.pop();
                stack.push(operator.operate(lhs, rhs));

                variables.replace(rhs.getName(), rhs);
                variables.replace(lhs.getName(), lhs);
            }
            else {
                Operand operand = (Operand)element;
                Optional.ofNullable(variables.get(operand.getName()))
                        .ifPresent(memorizedRhs -> operand.setNumericValue(memorizedRhs.getNumericValue()));
                stack.push(element);
            }
        }

        return (Operand)stack.pop();
    }
}
