package com.edgelab.hospital;

import com.edgelab.hospital.drugs.Drug;

import java.util.List;

public class HospitalSimulator {

    private final List<Patient> patients;

    HospitalSimulator(List<Patient> patients) {
        this.patients = patients;
    }

    void addPatient(Patient patient) {
        this.patients.add(patient);
    }

    void simulate(List<Drug> drugs) {
        patients.forEach(patient -> patient.treat(drugs));
    }

    String getResults() {
        return "";
    }

}
