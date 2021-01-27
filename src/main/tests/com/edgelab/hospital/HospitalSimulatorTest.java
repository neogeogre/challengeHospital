package com.edgelab.hospital;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Collections;

class HospitalSimulatorTest {

    @Test
    void parseState() {
        Assertions.assertEquals(State.FEVER, State.parseStates("F").get(0));
        Assertions.assertEquals(State.HEALTHY, State.parseStates("H").get(0));
        Assertions.assertEquals(State.DIABETES, State.parseStates("D").get(0));
        Assertions.assertEquals(State.TUBERCULOSIS, State.parseStates("T").get(0));
        Assertions.assertEquals(State.DEAD, State.parseStates("X").get(0));

        Assertions.assertEquals(Arrays.asList(State.FEVER, State.HEALTHY, State.DIABETES, State.TUBERCULOSIS, State.DEAD), State.parseStates("F,H,D,T,X"));
        Assertions.assertNotEquals(Arrays.asList(State.FEVER, State.HEALTHY, State.DIABETES, State.TUBERCULOSIS, State.DEAD), State.parseStates("H,F,D,T,X"));
    }

    @Test
    void parseDrug() {
        Assertions.assertEquals(Drug.ASPIRIN, Drug.parseDrugs("As").get(0));
        Assertions.assertEquals(Drug.ANTIBIOTIC, Drug.parseDrugs("An").get(0));
        Assertions.assertEquals(Drug.INSULIN, Drug.parseDrugs("I").get(0));
        Assertions.assertEquals(Drug.PARACETAMOL, Drug.parseDrugs("P").get(0));

        Assertions.assertEquals(Arrays.asList(Drug.ASPIRIN, Drug.ANTIBIOTIC, Drug.INSULIN, Drug.PARACETAMOL), Drug.parseDrugs("As,An,I,P"));
        Assertions.assertNotEquals(Arrays.asList(Drug.ASPIRIN, Drug.ANTIBIOTIC, Drug.INSULIN, Drug.PARACETAMOL), Drug.parseDrugs("An,As,I,P"));
    }

    @Test
    void useWrongMedicine() {
        Patient patient = new Patient(State.TUBERCULOSIS);
        patient.treat(Collections.singletonList(Drug.INSULIN), HospitalSimulator.CURE);
        Assertions.assertEquals(State.TUBERCULOSIS, patient.getState());
    }

    @Test
    void cureFever1() {
        Patient patient = new Patient(State.FEVER);
        patient.treat(Collections.singletonList(Drug.ASPIRIN), HospitalSimulator.CURE);
        Assertions.assertEquals(State.HEALTHY, patient.getState());
    }

    @Test
    void cureFever2() {
        Patient patient = new Patient(State.FEVER);
        patient.treat(Collections.singletonList(Drug.PARACETAMOL), HospitalSimulator.CURE);
        Assertions.assertEquals(State.HEALTHY, patient.getState());
    }

    @Test
    void cureTuberculosis() {
        Patient patient = new Patient(State.TUBERCULOSIS);
        patient.treat(Collections.singletonList(Drug.ANTIBIOTIC), HospitalSimulator.CURE);
        Assertions.assertEquals(State.HEALTHY, patient.getState());
    }

    @Test
    void cureDiabetes() {
        Patient patient = new Patient(State.DIABETES);
        patient.treat(Collections.singletonList(Drug.INSULIN), HospitalSimulator.CURE);
        Assertions.assertEquals(State.DIABETES, patient.getState());
    }

    @Test
    void diabetesDiesWithNoInsulin() {
        Patient patient = new Patient(State.DIABETES);
        patient.treat(Collections.singletonList(Drug.ANTIBIOTIC), HospitalSimulator.DEATH_EFFECT);
        Assertions.assertEquals(State.DEAD, patient.getState());
    }

    @Test
    void mixInsulinAntibiotic() {
        Patient patient = new Patient(State.HEALTHY);
        patient.treat(Arrays.asList(Drug.INSULIN, Drug.ANTIBIOTIC), HospitalSimulator.SIDE_EFFECT);
        Assertions.assertEquals(State.FEVER, patient.getState());
    }

    @Test
    void mixParacetamolAspirin() {
        Patient patient2 = new Patient(State.HEALTHY);
        patient2.treat(Arrays.asList(Drug.PARACETAMOL, Drug.ASPIRIN), HospitalSimulator.DEATH_EFFECT);
        Assertions.assertEquals(State.DEAD, patient2.getState());
    }

    @Test
    void cureDeath() {
        Patient patient = new Patient(State.DEAD);
        for (int i = 0; i < 10000000; i++) {
            patient.treat(Collections.singletonList(Drug.ANTIBIOTIC), HospitalSimulator.CURE);
        }
        Assertions.assertEquals(State.HEALTHY, patient.getState());
    }

    @Test
    void stateChangeOnce() {
        Patient patient = new Patient(State.HEALTHY);
        patient.treat(Arrays.asList(Drug.INSULIN, Drug.ANTIBIOTIC), HospitalSimulator.SIDE_EFFECT);
        Assertions.assertEquals(State.FEVER, patient.getState());
        patient.treat(Collections.singletonList(Drug.ASPIRIN), HospitalSimulator.CURE);
        Assertions.assertEquals(State.FEVER, patient.getState());
    }

    @Test
    void DeathTakePrecedence() {
        HospitalSimulator hospital = new HospitalSimulator(Collections.singletonList(new Patient(State.FEVER)));
        hospital.runSimulation(Arrays.asList(Drug.PARACETAMOL, Drug.ASPIRIN));
        Assertions.assertEquals(State.DEAD, hospital.patients.get(0).getState());
    }

    @Test
    void integrationTests() {
        Assertions.assertEquals("F:0,H:0,D:0,T:0,X:2", Application.run("D,D", ""));
        Assertions.assertEquals("F:0,H:1,D:0,T:0,X:0", Application.run("F", "P"));
    }

}