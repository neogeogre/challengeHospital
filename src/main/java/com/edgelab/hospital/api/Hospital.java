package com.edgelab.hospital.api;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Hospital {

    private final List<Patient> patients;

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
        this.patients.parallelStream().forEach(patient -> patient.treat(drugs));
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