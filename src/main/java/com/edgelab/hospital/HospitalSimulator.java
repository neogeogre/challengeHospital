package com.edgelab.hospital;

import java.util.List;
import java.util.Random;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HospitalSimulator {

    private List<Patient> patients;

    /**
     * A BiFunction for the conditions that will cause death to a patient
     */
    private final BiFunction<List<Drug>, State, State> deathEffect = (drug, state) -> {
        if (drug.contains(Drug.P) && drug.contains(Drug.As)) return State.X;
        if (!drug.contains(Drug.I) && state.equals(State.D)) return State.X;
        return state;
    };

    /**
     * A BiFunction for the conditions that will cause side effects to a patient
     */
    private final BiFunction<List<Drug>, State, State> sideEffect = (drug, state) -> {
        if (drug.contains(Drug.I) && drug.contains(Drug.An) && state.equals(State.H)) return State.F;
        return state;
    };

    /**
     * A BiFunction for the conditions to cure a patient
     */
    private final BiFunction<List<Drug>, State, State> cure = (drug, state) -> {
        if (drug.contains(Drug.As) && state.equals(State.F)) return State.H;
        if (drug.contains(Drug.P) && state.equals(State.F)) return State.H;
        if (drug.contains(Drug.An) && state.equals(State.T)) return State.H;
        if (state.equals(State.X)) {
            if (new Random().nextDouble() <= 0.000001) {
                return State.H;
            }
        }
        return state;
    };

    HospitalSimulator(List<Patient> patients) {
        this.patients = patients;
    }

    /**
     * the simulation respects the processing rules order. It is easy to integrate new rules
     * or modify the existing ones.
     *
     * @param drugs the list of drugs to inoculate to the patients of the hospital.
     */
    public void simulate(List<Drug> drugs) {
        this.patients = this.patients.parallelStream()
                .peek(patient -> patient.treat(drugs, this.deathEffect))
                .peek(patient -> patient.treat(drugs, this.sideEffect))
                .peek(patient -> patient.treat(drugs, this.cure))
                .collect(Collectors.toList());
    }

    /**
     * Generate the String of the resulting report for the Hospital
     */
    public String getResults() {
        return Stream.of(State.values()).map(state -> {
            long amount = this.patients.stream()
                    .filter(patient -> patient.getState().equals(state))
                    .count();
            return state.name() + ":" + amount;
        }).collect(Collectors.joining(","));
    }

}