package com.edgelab.hospital;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public enum State {

    F("Fever"),
    H("Healthy"),
    D("Diabetes"),
    T("Tuberculosis"),
    X("Dead");

    State(String name) {
    }

    public static List<State> parse(String input) {
        List<String> parts = Arrays.asList(input.split(","));
        return parts.stream()
                .map(str -> {try {return State.valueOf(str);} catch(RuntimeException e) {return null;}})
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

}
