package com.abl.rtbc.simplifier;

import com.abl.rtbc.service.calculator.ReversePolishNotationCalculator;
import com.abl.rtbc.service.converter.ReversePolishNotationConverter;
import com.abl.rtbc.service.simplifier.InfixSimplifier;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

public class s {
    /*i = 0
j = ++ i
x = i++ + 5
y = 5 + 3 * 10
i += y*/
    @Test
    public void x() {
        String[] x = """
                y = 5 + 3 * 10
                """.lines().toArray(String[]::new);

        var rpn = new ReversePolishNotationConverter();
        var rpn2 = new ReversePolishNotationCalculator();
        var f = new InfixSimplifier().simplify(x)
                .map(rpn::convert)
                .map(rpn2::calculate);
        StepVerifier.create(f)
                .expectNextCount(4)
                .verifyComplete();
    }
}
