package com.abl.rtbc.service;


import com.abl.rtbc.model.CalculatorRequest;
import com.abl.rtbc.model.CalculatorResponse;
import com.abl.rtbc.service.calculator.ReversePolishNotationCalculator;
import com.abl.rtbc.service.converter.ReversePolishNotationConverter;
import com.abl.rtbc.service.simplifier.InfixSimplifier;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CalculationService {

    private final InfixSimplifier simplifier;
    private final ReversePolishNotationConverter converter;
    private final ReversePolishNotationCalculator calculator;

    public Mono<CalculatorResponse> calculate(CalculatorRequest request) {
        return simplifier.simplify(request.getBody())
                .map(converter::convert)
                .collectList()
                .map(calculator::calculate);
    }
}
