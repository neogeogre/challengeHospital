package com.edgelab.hospital.entities;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * State contains the known states of a patient,
 * just add an enum in the list if needed.
 */
public enum State {

    FEVER("F"),
    HEALTHY("H"),
    DIABETES("D"),
    TUBERCULOSIS("T"),
    DEAD("X");

    public final String statusCode;

    State(String statusCode) {
        this.statusCode = statusCode;
    }

    public static List<State> parseStates(String input) {
        return  Arrays.stream(input.split(","))
                .map(State::parseState)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    private static Optional<State> parseState(String stateString) {
        return Stream.of(State.values())
                .filter(state -> state.statusCode.equals(stateString))
                .findFirst();
    }

}