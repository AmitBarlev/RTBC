package com.abl.rtbc.service;


import com.abl.rtbc.model.CalculatorRequest;
import com.abl.rtbc.model.CalculatorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CalculationService {


    public Mono<CalculatorResponse> calculate(CalculatorRequest request) {
        return Mono.empty();
    }
}
