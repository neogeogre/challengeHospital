package com.edgelab.hospital;

import com.edgelab.hospital.entities.Drug;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class DrugsTests {

    @Test
    void emptyDrug() {
        assertEquals(new ArrayList<>(), Drug.parseDrugs(""));
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
