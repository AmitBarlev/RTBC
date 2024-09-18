package com.abl.rtbc.service.calculator;

import com.abl.rtbc.model.CalculatorResponse;
import com.abl.rtbc.model.converter.RPNStack;
import com.abl.rtbc.model.simplifier.Operand;
import com.abl.rtbc.model.simplifier.Variable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service

public class ReversePolishNotationCalculator {

    public CalculatorResponse calculate(List<RPNStack> stacks) {
        Map<String, Operand> variables = new HashMap<>();
        stacks.forEach(stack -> calculateLine(stack, variables));
        Map<String, Double> output = variables
                .entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().getNumericValue()));

        return new CalculatorResponse(output);
    }

    private void calculateLine(RPNStack rpnStack, Map<String, Operand> variables) {

        while (!rpnStack.isEmpty()) {

            Operand rhs = rpnStack.popOperand();
            Operand lhs = rpnStack.popOperand();

            Optional.ofNullable(variables.get(rhs.getName()))
                    .ifPresent(memorizedRhs -> rhs.setNumericValue(memorizedRhs.getNumericValue()));
            Optional.ofNullable(variables.get(lhs.getName()))
                    .ifPresent(memorizedLhs -> lhs.setNumericValue(memorizedLhs.getNumericValue()));

            rpnStack.push(rpnStack.popOperator().operate(lhs, rhs));

            variables.replace(rhs.getName(), rhs);
            variables.replace(lhs.getName(), lhs);
        }

        Variable variable = (Variable) rpnStack.popOperand();

        variables.put(variable.getName(), variable);
    }
}
