package com.edgelab.hospital;

import com.edgelab.hospital.entities.Drug;
import com.edgelab.hospital.entities.Patient;
import com.edgelab.hospital.entities.State;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class PatientTests {

    @Test
    void nullPatient() {
        Patient patient = new Patient(null);
        assertThrows(NullPointerException.class, () -> patient.treat(null));
    }

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
    @Disabled
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
    void diabetesSurvivesWithInsulin() {
        Patient patient = new Patient(State.DIABETES);
        patient.treat(Collections.singletonList(Drug.INSULIN));
        assertEquals(State.DIABETES, patient.getState());
    }

    @Test
    void mixInsulinWithAntibiotic() {
        Patient patient = new Patient(State.HEALTHY);
        patient.treat(Arrays.asList(Drug.INSULIN, Drug.ANTIBIOTIC));
        assertEquals(State.FEVER, patient.getState());
    }

    @Test
    void mixParacetamolWithAspirin() {
        Patient patient = new Patient(State.HEALTHY);
        patient.treat(Arrays.asList(Drug.PARACETAMOL, Drug.ASPIRIN));
        assertEquals(State.DEAD, patient.getState());
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

}
