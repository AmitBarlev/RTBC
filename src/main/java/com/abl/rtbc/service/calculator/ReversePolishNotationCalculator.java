package com.abl.rtbc.service.calculator;

import com.abl.rtbc.model.converter.RPNStack;
import com.abl.rtbc.model.simplifier.EquationElement;
import com.abl.rtbc.model.simplifier.Operand;
import com.abl.rtbc.model.simplifier.Operator;
import com.abl.rtbc.model.simplifier.Variable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

@Service
public class ReversePolishNotationCalculator {

    public Map.Entry<String, Long> calculate(RPNStack rpnStack) {

        while (!rpnStack.isEmpty()) {

            Operand rhs = rpnStack.popOperand();
            Operand lhs = rpnStack.popOperand();
            rpnStack.push(rpnStack.popOperator().operate(lhs, rhs));
        }

        Variable result = (Variable)rpnStack.popOperand();

        return Map.entry(String.valueOf(result.value()), result.getNumericValue());
    }
}
