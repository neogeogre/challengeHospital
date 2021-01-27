package com.edgelab.hospital;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class HospitalTests {

    @Test
    void parseStates() {
        assertEquals(State.FEVER, State.parseStates("F").get(0));
        assertEquals(State.HEALTHY, State.parseStates("H").get(0));
        assertEquals(State.DIABETES, State.parseStates("D").get(0));
        assertEquals(State.TUBERCULOSIS, State.parseStates("T").get(0));
        assertEquals(State.DEAD, State.parseStates("X").get(0));

        assertEquals(Arrays.asList(State.FEVER, State.HEALTHY, State.DIABETES, State.TUBERCULOSIS, State.DEAD), State.parseStates("F,H,D,T,X"));
        assertNotEquals(Arrays.asList(State.FEVER, State.HEALTHY, State.DIABETES, State.TUBERCULOSIS, State.DEAD), State.parseStates("H,F,D,T,X"));
    }

    @Test
    void parseDrugs() {
        assertEquals(Drug.ASPIRIN, Drug.parseDrugs("As").get(0));
        assertEquals(Drug.ANTIBIOTIC, Drug.parseDrugs("An").get(0));
        assertEquals(Drug.INSULIN, Drug.parseDrugs("I").get(0));
        assertEquals(Drug.PARACETAMOL, Drug.parseDrugs("P").get(0));

        assertEquals(Arrays.asList(Drug.ASPIRIN, Drug.ANTIBIOTIC, Drug.INSULIN, Drug.PARACETAMOL), Drug.parseDrugs("As,An,I,P"));
        assertNotEquals(Arrays.asList(Drug.ASPIRIN, Drug.ANTIBIOTIC, Drug.INSULIN, Drug.PARACETAMOL), Drug.parseDrugs("An,As,I,P"));
    }

    @Test
    void medicineNoEffect() {
        Patient patient = new Patient(State.TUBERCULOSIS);
        patient.treat(Collections.singletonList(Drug.INSULIN), Hospital.CURE);
        assertEquals(State.TUBERCULOSIS, patient.getState());
    }

    @Test
    void cureFeverWithAspirin() {
        Patient patient = new Patient(State.FEVER);
        patient.treat(Collections.singletonList(Drug.ASPIRIN), Hospital.CURE);
        assertEquals(State.HEALTHY, patient.getState());
    }

    @Test
    void cureFeverWithParacetamol() {
        Patient patient = new Patient(State.FEVER);
        patient.treat(Collections.singletonList(Drug.PARACETAMOL), Hospital.CURE);
        assertEquals(State.HEALTHY, patient.getState());
    }

    @Test
    void cureTuberculosis() {
        Patient patient = new Patient(State.TUBERCULOSIS);
        patient.treat(Collections.singletonList(Drug.ANTIBIOTIC), Hospital.CURE);
        assertEquals(State.HEALTHY, patient.getState());
    }

    @Test
    void cureDiabetes() {
        Patient patient = new Patient(State.DIABETES);
        patient.treat(Collections.singletonList(Drug.INSULIN), Hospital.CURE);
        assertEquals(State.DIABETES, patient.getState());
    }

    @Test
    void cureDeath() {
        Random randomMock = Mockito.mock(Random.class);
        when(randomMock.nextDouble()).thenReturn(0.000002).thenReturn(0.0000009);
        Hospital.RANDOM = randomMock;
        Patient patient = new Patient(State.DEAD);
        patient.treat(Collections.singletonList(Drug.ANTIBIOTIC), Hospital.CURE);
        assertEquals(State.DEAD, patient.getState());
        patient.treat(Collections.singletonList(Drug.ANTIBIOTIC), Hospital.CURE);
        assertEquals(State.HEALTHY, patient.getState());
    }

    @Test
    void diabetesDiesWithoutInsulin() {
        Patient patient = new Patient(State.DIABETES);
        patient.treat(Collections.singletonList(Drug.ANTIBIOTIC), Hospital.DEATH_EFFECT);
        assertEquals(State.DEAD, patient.getState());
    }

    @Test
    void mixInsulinAntibiotic() {
        Patient patient = new Patient(State.HEALTHY);
        patient.treat(Arrays.asList(Drug.INSULIN, Drug.ANTIBIOTIC), Hospital.SIDE_EFFECT);
        assertEquals(State.FEVER, patient.getState());
    }

    @Test
    void mixParacetamolAspirin() {
        Patient patient2 = new Patient(State.HEALTHY);
        patient2.treat(Arrays.asList(Drug.PARACETAMOL, Drug.ASPIRIN), Hospital.DEATH_EFFECT);
        assertEquals(State.DEAD, patient2.getState());
    }

    @Test
    void stateChangeOnce() {
        Patient patient = new Patient(State.HEALTHY);
        patient.treat(Arrays.asList(Drug.INSULIN, Drug.ANTIBIOTIC), Hospital.SIDE_EFFECT);
        assertEquals(State.FEVER, patient.getState());
        patient.treat(Collections.singletonList(Drug.ASPIRIN), Hospital.CURE);
        assertEquals(State.FEVER, patient.getState());
    }

    @Test
    void DeathTakePrecedence() {
        Hospital hospital = new Hospital(Collections.singletonList(new Patient(State.FEVER)));
        hospital.runSimulation(Arrays.asList(Drug.PARACETAMOL, Drug.ASPIRIN));
        assertEquals(State.DEAD, hospital.patients.get(0).getState());
    }

    @Test
    void checkHospitalResultsGeneration() {
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
    void integrationTests() {
        assertEquals("F:0,H:0,D:0,T:0,X:2", Application.run("D,D", ""));
        assertEquals("F:0,H:1,D:0,T:0,X:0", Application.run("F", "P"));
        assertEquals("F:0,H:3,D:0,T:0,X:1", Application.run("F,D,T,T", "P,An"));
    }

}