package com.edgelab.hospital;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * State contains the known states of a patient,
 * just add an enum in the list if needed.
 */
public enum State {

    F("Fever"),
    H("Healthy"),
    D("Diabetes"),
    T("Tuberculosis"),
    X("Dead");

    State(String name) {
    }

    public static List<State> parse(String input) {
        var parts = Arrays.asList(input.split(","));
        return parts.stream()
                .map(str -> {
                    try {
                        return State.valueOf(str);
                    } catch (Exception e) {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

}
