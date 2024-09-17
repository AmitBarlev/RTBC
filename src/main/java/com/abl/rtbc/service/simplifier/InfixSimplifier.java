package com.abl.rtbc.service.simplifier;

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
    private final static Character PLUS = '+';
    private final static Character MINUS = '-';
    private final static Character MULTIPLE = '*';
    private final static Character DIVIDE = '/';
    private final static String DECREMENT = "--";
    private final static String INCREMENT = "++";
    private final static String WHITESPACE = " ";
    private final static String EMPTY = "";

    public Flux<List<EquationElement>> simplify(String[] lines){
        return Flux.fromArray(lines)
                .filter(StringUtils::hasText)
                .map(this::simplify);
    }

    private List<EquationElement> simplify(String infix) {
        List<EquationElement> expressions = new ArrayList<>();
        infix = infix.replace(WHITESPACE, EMPTY);

        for (String current: splitByEquals(infix)) {

            int index = 0;
            while (index < current.length()) {

                EquationElement element;
                char c = current.charAt(index);
                if (Character.isDigit(c)){
                    element = getNumberElement(index, current);
                }
                else if (Character.isLetter(c)){
                    element = getVariableElement(index, current);
                }
                else if (isPreOperator(current, index)){
                    element = getPreOperandVariable(index, current);
                }
                else if (isEqualsOperator(current)){
                    element = new EqualsOperator(current, ElementType.EQUALS);
                }
                else if (isOperator(c)){ //operator
                    element = new Operator(String.valueOf(c), ElementType.OPERATOR);
                }
                else if ('(' == c){
                    element = new EquationElement(String.valueOf(c), ElementType.OPENING_BRACKET);
                }
                else if (')' == c){
                    element = new EquationElement(String.valueOf(c), ElementType.CLOSING_BRACKET);
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

            for (String sign: SIGNS) {
                forward = line.indexOf(sign, backward);
                if (-1 == forward)
                    continue;

                chunks.add(line.substring(backward, forward));
                chunks.add(line.substring(forward, forward + sign.length()));
                backward = forward + sign.length();
            }
        }

        chunks.add(line.substring(backward));
        return chunks;
    }

    private EquationElement getNumberElement(int index, String infix) {
        int forward = index;
        while (forward < infix.length() && Character.isDigit(infix.charAt(forward))){
            ++forward;
        }

        return new Operand(infix.substring(index, forward), ElementType.OPERAND);
    }

    private EquationElement getVariableElement(int index, String infix) {
        return new Variable(getVariableValue(index, infix), ElementType.OPERAND);
    }

    private String getVariableValue(int index, String infix) {
        int forward = index;
        while (forward < infix.length() && Character.isLetter(infix.charAt(forward))){
            ++forward;
        }

        String variable = infix.substring(index, forward);
        int postDecrement = infix.indexOf(variable + INCREMENT);
        postDecrement = -1 == postDecrement ? infix.indexOf(variable + DECREMENT) : postDecrement;
        int until = INCREMENT.length() + 1; //because substring is not inclusive;
        return -1 == postDecrement ? variable : infix.substring(index, until);
    }

    private EquationElement getPreOperandVariable(int index, String infix) {
        String value = infix.substring(index, index + 2) + getVariableValue(index + 2, infix);
        return new Variable(value, ElementType.OPERAND);
    }

    private boolean isOperator(Character c) {
        return PLUS == c || MINUS == c || DIVIDE == c || MULTIPLE == c;
    }

    private boolean isEqualsOperator(String current) {
        return Arrays.asList(SIGNS).contains(current);
    }

    private boolean isPreOperator(String infix, int index){
        return infix.substring(index).startsWith(INCREMENT) ||
                infix.substring(index).startsWith(DECREMENT); //add minus
    }
}
