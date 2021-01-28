package com.edgelab.hospital;

import com.edgelab.hospital.api.Drug;
import com.edgelab.hospital.api.State;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class DrugsAndStatesTests {

    @Test
    void parseFever() {
        assertEquals(State.FEVER, State.parseStates("F").get(0));
    }

    @Test
    void parseHealthy() {
        assertEquals(State.HEALTHY, State.parseStates("H").get(0));
    }

    @Test
    void parseDiabetes() {
        assertEquals(State.DIABETES, State.parseStates("D").get(0));
    }

    @Test
    void parseTuberculosis() {
        assertEquals(State.TUBERCULOSIS, State.parseStates("T").get(0));
    }

    @Test
    void parseMultipleStates() {
        assertEquals(Arrays.asList(State.FEVER, State.HEALTHY, State.DIABETES, State.TUBERCULOSIS, State.DEAD), State.parseStates("F,H,D,T,X"));
        assertNotEquals(Arrays.asList(State.FEVER, State.HEALTHY, State.DIABETES, State.TUBERCULOSIS, State.DEAD), State.parseStates("H,F,D,T,X"));
    }

    @Test
    void parseAspirin() {
        assertEquals(Drug.ASPIRIN, Drug.parseDrugs("As").get(0));
    }

    @Test
    void parseAntibiotic() {
        assertEquals(Drug.ANTIBIOTIC, Drug.parseDrugs("An").get(0));
    }

    @Test
    void parseInsulin() {
        assertEquals(Drug.INSULIN, Drug.parseDrugs("I").get(0));
    }

    @Test
    void parseParacetamol() {
        assertEquals(Drug.PARACETAMOL, Drug.parseDrugs("P").get(0));
    }

    @Test
    void parseMultipleDrugs() {
        assertEquals(Arrays.asList(Drug.ASPIRIN, Drug.ANTIBIOTIC, Drug.INSULIN, Drug.PARACETAMOL), Drug.parseDrugs("As,An,I,P"));
        assertNotEquals(Arrays.asList(Drug.ASPIRIN, Drug.ANTIBIOTIC, Drug.INSULIN, Drug.PARACETAMOL), Drug.parseDrugs("An,As,I,P"));
    }

}
