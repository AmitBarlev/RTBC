package com.abl.rtbc.service.simplifier;

import com.abl.rtbc.model.simplifier.Number;
import com.abl.rtbc.model.simplifier.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class InfixSimplifier {

    private final static String[] SIGNS = new String[] {"+=", "-=", "/=", "*=", "="};
    private final static String ASSIGNMENT = "=";
    private final static Character PLUS = '+';
    private final static Character MINUS = '-';
    private final static Character MULTIPLE = '*';
    private final static Character DIVIDE = '/';
    private final static String DECREMENT = "--";
    private final static String TRIPLE_MINUS = "---";
    private final static String INCREMENT = "++";
    private final static String WHITESPACE = " ";
    private final static String EMPTY = "";

    public Flux<List<AlgebraicExpressionElement>> simplify(String lines){
        return Flux.fromStream(lines.lines())
                .filter(StringUtils::hasText)
                .map(this::simplifyLine);
    }

    private List<AlgebraicExpressionElement> simplifyLine(String infix) {
        List<AlgebraicExpressionElement> expressions = new ArrayList<>();
        infix = infix.replace(WHITESPACE, EMPTY);

        for (String current: splitByEquals(infix)) {

            int index = 0;
            while (index < current.length()) {

                AlgebraicExpressionElement element;
                char c = current.charAt(index);
                if (Character.isDigit(c)){
                    element = getNumberElement(index, current);
                }
                else if (Character.isLetter(c)){
                    element = getVariableElement(index, current);
                }
                else if (isEqualsOperator(current)){
                    element = new AssignmentOperator(getAssignmentOperator(current));
                }
                else if (isPreOperator(current, index)){
                    element = getPreOperandVariable(index, current);
                }
                else if (isOperator(c) && verifyNotTwoOperator(expressions)) {
                    element = new Operator(String.valueOf(c));
                }
                else if ('(' == c){
                    element = new Bracket(ElementType.OPENING_BRACKET);
                }
                else if (')' == c){
                    element = new Bracket(ElementType.CLOSING_BRACKET);
                }
                else {
                    throw new RuntimeException("Unidentified expression");
                }

                index += element.getValue().length();
                expressions.add(element);

            }
        }

        return expressions;
    }

    private List<String> splitByEquals(String line) {
        List<String> chunks = new ArrayList<>();
        int backward = 0;
        int forward = 0;

        while (-1 != forward) {

            forward = line.indexOf(ASSIGNMENT, backward);
            if (-1 == forward)
                continue;

            boolean isTwoCharacterAssignment = isOperator(line.charAt(forward - 1));
            int signLength = isTwoCharacterAssignment ? 2 : 1;

            forward = isTwoCharacterAssignment ? forward - 1 : forward;
            chunks.add(line.substring(backward, forward));
            chunks.add(line.substring(forward, forward + signLength));
            backward = forward + signLength;
        }

        chunks.add(line.substring(backward));
        return chunks;
    }

    private String getAssignmentOperator(String current) {
        int index;
        for (String sign: SIGNS) {
            index = current.indexOf(sign);
            if (-1 != index)
                return current.substring(index, index + sign.length());
        }

        return null;
    }

    private AlgebraicExpressionElement getNumberElement(int index, String infix) {
        int forward = index;
        while (forward < infix.length() && Character.isDigit(infix.charAt(forward))){
            ++forward;
        }

        if (forward < infix.length() && Character.isLetter(infix.charAt(forward)))
            throw new RuntimeException("Invalid number definition");

        return new Number(infix.substring(index, forward));
    }

    private AlgebraicExpressionElement getVariableElement(int index, String infix) {
        return new Variable(getVariableValue(index, infix));
    }

    private String getVariableValue(int index, String infix) {
        int forward = index;
        while (forward < infix.length() && Character.isLetterOrDigit(infix.charAt(forward))){
            ++forward;
        }

        String variable = infix.substring(index, forward);
        int postDecrement = infix.indexOf(variable + INCREMENT);
        postDecrement = -1 == postDecrement ? infix.indexOf(variable + DECREMENT) : postDecrement;
        int until = INCREMENT.length() + 1; //because substring is not inclusive;
        return -1 == postDecrement ? variable : infix.substring(index, until);
    }

    private AlgebraicExpressionElement getPreOperandVariable(int index, String infix) {
        int preOperandLength = index;
        if (infix.startsWith(TRIPLE_MINUS)) {
            throw new RuntimeException("Pre operator cannot be applied");
        }

        if (MINUS == infix.charAt(preOperandLength)) {
            preOperandLength += 1;
        }

        if (infix.startsWith(INCREMENT, preOperandLength) || infix.startsWith(DECREMENT, preOperandLength)) {
            preOperandLength += 2;
            if (!Character.isLetter(infix.charAt(preOperandLength)))
                throw new RuntimeException("Pre operators cannot be applied to anything other than variables");
        }

        String value = infix.substring(index, preOperandLength) + getVariableValue(preOperandLength, infix);
        return new Variable(value);
    }

    private boolean isOperator(Character c) {
        return PLUS == c || MINUS == c || DIVIDE == c || MULTIPLE == c;
    }

    private boolean isEqualsOperator(String current) {
        return Arrays.stream(SIGNS).anyMatch(current::contains);
    }

    private boolean isPreOperator(String expression, int index){
        if (expression.startsWith(INCREMENT) || expression.equals(DECREMENT)) {
            return true;
        }

        boolean isOperatorOrBracketsBefore = index > 0 && (isOperator(expression.charAt(index - 1)) ||
                '(' == expression.charAt(index - 1) || ')' == expression.charAt(index - 1));

        boolean isDigitOrIncrementAfter = index + 1 < expression.length() &&
                Character.isLetterOrDigit(expression.charAt(index + 1)) ||
                expression.substring(index + 1).startsWith(INCREMENT);

        return 0 == index && MINUS == expression.charAt(index) ||
                isOperatorOrBracketsBefore &&
                MINUS == expression.charAt(index) &&
                        isDigitOrIncrementAfter;
    }

    private boolean verifyNotTwoOperator(List<AlgebraicExpressionElement> expressions) {
        return ElementType.OPERATOR != expressions.get(expressions.size() - 1).getType();
    }
}
