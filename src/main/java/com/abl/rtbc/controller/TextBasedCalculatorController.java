package com.abl.rtbc.controller;


import com.abl.rtbc.model.CalculatorRequest;
import com.abl.rtbc.model.CalculatorResponse;
import com.abl.rtbc.service.CalculationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "v1/")
public class TextBasedCalculatorController {

    private final CalculationService service;

    @PostMapping(value = "calculate")
    public Mono<CalculatorResponse> calculate(CalculatorRequest request) {
        return service.calculate(request);
    }
}
