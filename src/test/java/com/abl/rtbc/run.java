package com.abl.rtbc;

import com.abl.rtbc.service.calculator.ReversePolishNotationCalculator;
import com.abl.rtbc.service.organizer.ReversePolishNotationOrganizer;
import com.abl.rtbc.service.simplifier.InfixSimplifier;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

public class run {

    @Test
    public void x() {
        var infix = new InfixSimplifier();
        var rpn = new ReversePolishNotationOrganizer();
        var cal = new ReversePolishNotationCalculator();

        var s = """
                i = 0
                j = ++ i
                x = i++ + 5
                y = 5 + 3 * 10
                i += y
                """;

        var f = infix.simplify(s)
                .map(rpn::organize)
                .collectList()
                .map(cal::calculate);
        StepVerifier.create(f)
                .expectNextCount(4)
                .verifyComplete();
    }
}
