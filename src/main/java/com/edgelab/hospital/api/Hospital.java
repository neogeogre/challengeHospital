package com.edgelab.hospital.api;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * An patient of the {@link com.edgelab.hospital.api.Hospital} host a list of {@link com.edgelab.hospital.api.Patient}
 * We can run a simulation on the patients using a list of {@link com.edgelab.hospital.api.Drug}.
 * A report will be generated.
 */
@EqualsAndHashCode
@ToString
public class Hospital {

    private final List<Patient> patients;

    public Hospital(List<Patient> patients) {
        this.patients = patients;
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