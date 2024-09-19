package com.abl.rtbc.service;


import com.abl.rtbc.model.CalculatorRequest;
import com.abl.rtbc.model.CalculatorResponse;
import com.abl.rtbc.model.organizer.RPNPayload;
import com.abl.rtbc.model.simplifier.AlgebraicExpressionElement;
import com.abl.rtbc.service.calculator.ReversePolishNotationCalculator;
import com.abl.rtbc.service.organizer.ReversePolishNotationOrganizer;
import com.abl.rtbc.service.simplifier.InfixSimplifier;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.HashMap;
import java.util.List;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

public class CalculatorServiceTest {

    @InjectMocks
    private CalculationService service;

    @Mock
    private InfixSimplifier simplifier;

    @Mock
    private ReversePolishNotationOrganizer organizer;

    @Mock
    private ReversePolishNotationCalculator calculator;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    @SneakyThrows
    void tearDown() {
        closeable.close();
    }

    @Test
    public void calculate_sanity_calculatorResponse() {
        CalculatorRequest request = new CalculatorRequest("");
        CalculatorResponse response = new CalculatorResponse(new HashMap<>());
        List<AlgebraicExpressionElement> simpliferOutput = List.of();
        RPNPayload organizerOutput = new RPNPayload();
        List<RPNPayload> calculatorInput = List.of(organizerOutput);


        doReturn(Flux.just(simpliferOutput)).when(simplifier).simplify(request.getBody());
        doReturn(organizerOutput).when(organizer).organize(simpliferOutput);
        doReturn(response).when(calculator).calculate(calculatorInput);

        Mono<CalculatorResponse> output = service.calculate(request);

        StepVerifier.create(output)
                .expectNextCount(1)
                .verifyComplete();

        verify(simplifier).simplify(request.getBody());
        verify(organizer).organize(simpliferOutput);
        verify(calculator).calculate(calculatorInput);
    }
}
