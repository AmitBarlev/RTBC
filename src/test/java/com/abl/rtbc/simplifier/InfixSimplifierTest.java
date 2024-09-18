package com.abl.rtbc.simplifier;

import com.abl.rtbc.model.simplifier.EquationElement;
import com.abl.rtbc.service.simplifier.InfixSimplifier;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class InfixSimplifierTest {

    private final InfixSimplifier infixSimplifier = new InfixSimplifier();

    @Test
    public void simplify_simpleAssignment_3Elements() {
        Flux<List<EquationElement>> output = infixSimplifier.simplify("i=0");

        StepVerifier.create(output)
                .assertNext(list -> {
                    assertEquals(3, list.size());
                    assertEquals("i", list.get(0).getValue());
                    assertEquals("=", list.get(1).getValue());
                    assertEquals("0", list.get(2).getValue());
                })
                .verifyComplete();
    }

    @Test
    public void simplify_assignmentAndSpacesAndPlusOperator_5Elements() {
        Flux<List<EquationElement>> output = infixSimplifier.simplify("i=     4 + 7");

        StepVerifier.create(output)
                .assertNext(list -> {
                    assertEquals(5, list.size());
                    assertEquals("i", list.get(0).getValue());
                    assertEquals("=", list.get(1).getValue());
                    assertEquals("4", list.get(2).getValue());
                    assertEquals("+", list.get(3).getValue());
                    assertEquals("7", list.get(4).getValue());
                })
                .verifyComplete();
    }

    @Test
    public void simplify_assignmentAndSpacesAndMinusOperator_7Elements() {
        Flux<List<EquationElement>> output = infixSimplifier.simplify("i=4 + 7   - 1");

        StepVerifier.create(output)
                .assertNext(list -> {
                    assertEquals(7, list.size());
                    assertEquals("i", list.get(0).getValue());
                    assertEquals("=", list.get(1).getValue());
                    assertEquals("4", list.get(2).getValue());
                    assertEquals("+", list.get(3).getValue());
                    assertEquals("7", list.get(4).getValue());
                    assertEquals("-", list.get(5).getValue());
                    assertEquals("1", list.get(6).getValue());
                })
                .verifyComplete();
    }

    @Test
    public void simplify_multipleAssignmentAndSpacesAndMinusOperator_3Elements() {
        Flux<List<EquationElement>> output = infixSimplifier.simplify("i*=4");

        StepVerifier.create(output)
                .assertNext(list -> {
                    assertEquals(3, list.size());
                    assertEquals("i", list.get(0).getValue());
                    assertEquals("*=", list.get(1).getValue());
                    assertEquals("4", list.get(2).getValue());
                })
                .verifyComplete();
    }

    @Test
    public void simplify_assignmentPreIncrement_3Elements() {
        Flux<List<EquationElement>> output = infixSimplifier.simplify("j = ++i");

        StepVerifier.create(output)
                .assertNext(list -> {
                    assertEquals(3, list.size());
                    assertEquals("j", list.get(0).getValue());
                    assertEquals("=", list.get(1).getValue());
                    assertEquals("++i", list.get(2).getValue());
                })
                .verifyComplete();
    }

    @Test
    public void simplify_assignmentPostDecrement_3Elements() {
        Flux<List<EquationElement>> output = infixSimplifier.simplify("j = i--");

        StepVerifier.create(output)
                .assertNext(list -> {
                    assertEquals(3, list.size());
                    assertEquals("j", list.get(0).getValue());
                    assertEquals("=", list.get(1).getValue());
                    assertEquals("i--", list.get(2).getValue());
                })
                .verifyComplete();
    }

    @Test
    public void simplify_expressionWithBrackets_9Elements() {
        Flux<List<EquationElement>> output = infixSimplifier.simplify("j = (5 + 3) * 3");

        StepVerifier.create(output)
                .assertNext(list -> {
                    assertEquals(9, list.size());
                    assertEquals("j", list.get(0).getValue());
                    assertEquals("=", list.get(1).getValue());
                    assertEquals("(", list.get(2).getValue());
                    assertEquals("5", list.get(3).getValue());
                    assertEquals("+", list.get(4).getValue());
                    assertEquals("3", list.get(5).getValue());
                    assertEquals(")", list.get(6).getValue());
                    assertEquals("*", list.get(7).getValue());
                    assertEquals("3", list.get(8).getValue());
                })
                .verifyComplete();
    }

    @Test
    public void simplify_twoLinesSimpleAssignmentAndAssignmentPreIncrement_2Lists3Elements() {
        String lines = """
                i = 0
                j = ++ i
                """;

        Flux<List<EquationElement>> output = infixSimplifier.simplify(lines);

        StepVerifier.create(output)
                .assertNext(list -> {
                    assertEquals(3, list.size());
                    assertEquals("i", list.get(0).getValue());
                    assertEquals("=", list.get(1).getValue());
                    assertEquals("0", list.get(2).getValue());
                })
                .assertNext(list -> {
                    assertEquals(3, list.size());
                    assertEquals("j", list.get(0).getValue());
                    assertEquals("=", list.get(1).getValue());
                    assertEquals("++i", list.get(2).getValue());
                })
                .verifyComplete();
    }

    @Test
    public void simplify_5Lines_5Lists() {
        String lines = """
                i = 0
                j = ++ i
                x = i++ + 5
                y = 5 + 3 * 10
                i += y
                """;

        Flux<List<EquationElement>> output = infixSimplifier.simplify(lines);

        StepVerifier.create(output)
                .assertNext(list -> {
                    assertEquals(3, list.size());
                    assertEquals("i", list.get(0).getValue());
                    assertEquals("=", list.get(1).getValue());
                    assertEquals("0", list.get(2).getValue());
                })
                .assertNext(list -> {
                    assertEquals(3, list.size());
                    assertEquals("j", list.get(0).getValue());
                    assertEquals("=", list.get(1).getValue());
                    assertEquals("++i", list.get(2).getValue());
                })
                .assertNext(list -> {
                    assertEquals(5, list.size());
                    assertEquals("x", list.get(0).getValue());
                    assertEquals("=", list.get(1).getValue());
                    assertEquals("i++", list.get(2).getValue());
                    assertEquals("+", list.get(3).getValue());
                    assertEquals("5", list.get(4).getValue());
                })
                .assertNext(list -> {
                    assertEquals(7, list.size());
                    assertEquals("y", list.get(0).getValue());
                    assertEquals("=", list.get(1).getValue());
                    assertEquals("5", list.get(2).getValue());
                    assertEquals("+", list.get(3).getValue());
                    assertEquals("3", list.get(4).getValue());
                    assertEquals("*", list.get(5).getValue());
                    assertEquals("10", list.get(6).getValue());
                })
                .assertNext(list -> {
                    assertEquals(3, list.size());
                    assertEquals("i", list.get(0).getValue());
                    assertEquals("+=", list.get(1).getValue());
                    assertEquals("y", list.get(2).getValue());
                })
                .verifyComplete();
    }

    @Test
    public void simplify_variableWithNumberInName_3Elements() {
        String lines = """
                i3 = 0
                """;

        Flux<List<EquationElement>> output = infixSimplifier.simplify(lines);

        StepVerifier.create(output)
                .assertNext(list -> {
                    assertEquals(3, list.size());
                    assertEquals("i3", list.get(0).getValue());
                    assertEquals("=", list.get(1).getValue());
                    assertEquals("0", list.get(2).getValue());
                })
                .verifyComplete();
    }

    @Test
    public void simplify_minusNumber_3Elements() {
        String lines = """
                i3 = -1
                """;

        Flux<List<EquationElement>> output = infixSimplifier.simplify(lines);

        StepVerifier.create(output)
                .assertNext(list -> {
                    assertEquals(3, list.size());
                    assertEquals("i3", list.get(0).getValue());
                    assertEquals("=", list.get(1).getValue());
                    assertEquals("0", list.get(2).getValue());
                })
                .verifyComplete();
    }

    @Test
    public void simplify_unknownCharacter_exceptionThrown() {
        String lines = """
                i = #
                """;

        Flux<List<EquationElement>> output = infixSimplifier.simplify(lines);

        StepVerifier.create(output)
                .verifyError(RuntimeException.class);
    }

    @Test
    public void simplify_numberWithLetterAttached_runtimeExceptionThrown() {
        String lines = """
                i3 = 23w
                """;

        Flux<List<EquationElement>> output = infixSimplifier.simplify(lines);

        StepVerifier.create(output)
                .verifyError(RuntimeException.class);;
    }

    @Test
    public void simplify_doubleMultipleOperator_runtimeExceptionThrown() {
        String lines = """
                i3 = 1 * * 3
                """;

        Flux<List<EquationElement>> output = infixSimplifier.simplify(lines);

        StepVerifier.create(output)
                .verifyError(RuntimeException.class);;
    }
}
