package com.edgelab.hospital;

import com.edgelab.hospital.api.Drug;
import com.edgelab.hospital.api.Hospital;
import com.edgelab.hospital.api.Patient;
import com.edgelab.hospital.api.State;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class PatientTests {

    @Test
    void medicineNoEffect() {
        Patient patient = new Patient(State.TUBERCULOSIS);
        patient.treat(Collections.singletonList(Drug.INSULIN));
        assertEquals(State.TUBERCULOSIS, patient.getState());
    }

    @Test
    void cureFeverWithAspirin() {
        Patient patient = new Patient(State.FEVER);
        patient.treat(Collections.singletonList(Drug.ASPIRIN));
        assertEquals(State.HEALTHY, patient.getState());
    }

    @Test
    void cureFeverWithParacetamol() {
        Patient patient = new Patient(State.FEVER);
        patient.treat(Collections.singletonList(Drug.PARACETAMOL));
        assertEquals(State.HEALTHY, patient.getState());
    }

    @Test
    void cureTuberculosis() {
        Patient patient = new Patient(State.TUBERCULOSIS);
        patient.treat(Collections.singletonList(Drug.ANTIBIOTIC));
        assertEquals(State.HEALTHY, patient.getState());
    }

    @Test
    void cureDiabetes() {
        Patient patient = new Patient(State.DIABETES);
        patient.treat(Collections.singletonList(Drug.INSULIN));
        assertEquals(State.DIABETES, patient.getState());
    }

    @Test
    void cureDeathRandomly() {
        Random randomMock = Mockito.mock(Random.class);
        when(randomMock.nextDouble()).thenReturn(0.0000011).thenReturn(0.0000010);
        Patient.RANDOM = randomMock;
        Patient patient = new Patient(State.DEAD);
        patient.treat(Collections.singletonList(Drug.ANTIBIOTIC));
        assertEquals(State.DEAD, patient.getState());
        patient.treat(Collections.singletonList(Drug.ANTIBIOTIC));
        assertEquals(State.HEALTHY, patient.getState());
    }

    @Test
    void diabetesDiesWithoutInsulin() {
        Patient patient = new Patient(State.DIABETES);
        patient.treat(Collections.singletonList(Drug.ANTIBIOTIC));
        assertEquals(State.DEAD, patient.getState());
    }

    @Test
    void mixInsulinWithAntibiotic() {
        Patient patient = new Patient(State.HEALTHY);
        patient.treat(Arrays.asList(Drug.INSULIN, Drug.ANTIBIOTIC));
        assertEquals(State.FEVER, patient.getState());
    }

    @Test
    void mixParacetamolWithAspirin() {
        Patient patient2 = new Patient(State.HEALTHY);
        patient2.treat(Arrays.asList(Drug.PARACETAMOL, Drug.ASPIRIN));
        assertEquals(State.DEAD, patient2.getState());
    }

    @Test
    void stateChangesOnlyOnce() {
        Patient patient = new Patient(State.HEALTHY);
        patient.treat(Arrays.asList(Drug.INSULIN, Drug.ANTIBIOTIC, Drug.ASPIRIN));
        assertEquals(State.FEVER, patient.getState());
    }

    @Test
    void DeathTakesPrecedence() {
        Patient patient = new Patient(State.FEVER);
        patient.treat(Arrays.asList(Drug.PARACETAMOL, Drug.ASPIRIN));
        assertEquals(State.DEAD, patient.getState());
    }

    @Test
    void checkHospitalReportGeneration() {
        Patient patient1 = Mockito.mock(Patient.class);
        Patient patient2 = Mockito.mock(Patient.class);
        Patient patient3 = Mockito.mock(Patient.class);

        when(patient1.getState()).thenReturn(State.FEVER);
        when(patient2.getState()).thenReturn(State.HEALTHY);
        when(patient3.getState()).thenReturn(State.TUBERCULOSIS);

        Hospital hospital = new Hospital(Arrays.asList(patient1, patient2, patient3));
        String results = hospital.runSimulation(Arrays.asList(Drug.ANTIBIOTIC, Drug.ASPIRIN));
        assertEquals("F:1,H:1,D:0,T:1,X:0", results);
    }

    @Test
    void multiSimulations() {
        List<Patient> patients = Arrays.asList(new Patient(State.FEVER), new Patient(State.DIABETES), new Patient(State.TUBERCULOSIS));
        Hospital hospital = new Hospital(patients);

        String results1 = hospital.runSimulation(Arrays.asList(Drug.PARACETAMOL, Drug.INSULIN));
        assertEquals("F:0,H:1,D:1,T:1,X:0", results1);

        String results2 = hospital.runSimulation(Arrays.asList(Drug.ANTIBIOTIC, Drug.ASPIRIN));
        assertEquals("F:0,H:2,D:0,T:0,X:1", results2);
    }

}