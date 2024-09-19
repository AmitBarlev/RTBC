package com.abl.rtbc.model;

import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.Map;

@Value
@RequiredArgsConstructor
public class CalculatorResponse {

    Map<String, Double> variables;
}
