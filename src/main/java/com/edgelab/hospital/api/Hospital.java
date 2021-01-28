package com.edgelab.hospital.api;

import java.util.List;
import java.util.Random;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Hospital {

    private final List<Patient> patients;

    public static Random RANDOM = new Random();

    /**
     * A BiFunction for the conditions that will cause death to a patient
     */
    public static final BiFunction<List<Drug>, State, State> DEATH_EFFECT = (drug, state) -> {
        if (drug.contains(Drug.PARACETAMOL) && drug.contains(Drug.ASPIRIN)) return State.DEAD;
        if (!drug.contains(Drug.INSULIN) && state.equals(State.DIABETES)) return State.DEAD;
        return state;
    };

    /**
     * A BiFunction for the conditions that will cause side effects to a patient
     */
    public static final BiFunction<List<Drug>, State, State> SIDE_EFFECT = (drug, state) -> {
        if (drug.contains(Drug.INSULIN) && drug.contains(Drug.ANTIBIOTIC) && state.equals(State.HEALTHY))
            return State.FEVER;
        return state;
    };

    /**
     * A BiFunction for the conditions to cure a patient
     */
    public static final BiFunction<List<Drug>, State, State> CURE = (drug, state) -> {
        if (drug.contains(Drug.ASPIRIN) && state.equals(State.FEVER)) return State.HEALTHY;
        if (drug.contains(Drug.PARACETAMOL) && state.equals(State.FEVER)) return State.HEALTHY;
        if (drug.contains(Drug.ANTIBIOTIC) && state.equals(State.TUBERCULOSIS)) return State.HEALTHY;
        if (state.equals(State.DEAD)) {
            if (RANDOM.nextDouble() <= 0.000001) {
                return State.HEALTHY;
            }
        }
        return state;
    };

    public Hospital(List<Patient> patients) {
        this.patients = patients;
    }

    public List<Patient> getPatients() {
        return this.patients;
    }

    /**
     * The simulation respects the processing rules order.
     * It is easy to integrate new rules or modify the existing ones.
     *
     * @param drugs the list of drugs to inoculate to the patients of the hospital.
     */
    public String runSimulation(List<Drug> drugs) {
        this.patients.parallelStream()
                .peek(patient -> patient.treat(drugs, DEATH_EFFECT))
                .peek(patient -> patient.treat(drugs, SIDE_EFFECT))
                .forEach(patient -> patient.treat(drugs, CURE));
        return generateReport();
    }

    private String generateReport() {
        return Stream.of(State.values())
                .map(this::amountOfPatientsPerState)
                .collect(Collectors.joining(","));
    }

    private String amountOfPatientsPerState(State state) {
        return state.statusCode + ":" + this.patients.stream()
                .filter(patient -> patient.getState().equals(state))
                .count();
    }

}