package com.edgelab.hospital.states;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

public enum States {
    F(Fever::new),
    H(Healthy::new),
    D(Diabetes::new),
    T(Tuberculosis::new),
    X(Dead::new);

    public final Supplier<State> factory;

    States(java.util.function.Supplier<State> factory) {
        this.factory = requireNonNull(factory);
    }

    public static List<State> pick(String patientstr) {
        return Arrays.stream(patientstr.split(","))
                .map(state -> States.valueOf(state).factory.get())
                .collect(Collectors.toList());
    }

}