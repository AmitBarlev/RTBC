package com.abl.rtbc.controller;

import com.abl.rtbc.model.CalculatorRequest;
import com.abl.rtbc.model.CalculatorResponse;
import com.abl.rtbc.service.CalculationService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class CalculatorControllerTest {

    @InjectMocks
    private TextBasedCalculatorController controller;

    @Mock
    private CalculationService service;

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

        doReturn(Mono.just(response)).when(service).calculate(request);

        Mono<CalculatorResponse> output = controller.calculate(request);

        StepVerifier.create(output)
                .expectNextCount(1)
                .verifyComplete();

        verify(service).calculate(request);
    }

    @Test
    public void calculate_sanity_calculator() {
        CalculatorRequest request = new CalculatorRequest("");

        doThrow(new RuntimeException()).when(service).calculate(request);

        assertThrows(RuntimeException.class, () -> controller.calculate(request));

        verify(service).calculate(request);
    }
}
