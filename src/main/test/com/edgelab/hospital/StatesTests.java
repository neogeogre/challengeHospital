package com.edgelab.hospital;

import com.edgelab.hospital.entities.State;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class StatesTests {

    @Test
    void emptyState() {
        assertEquals(new ArrayList<>(), State.parseStates(""));
    }

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

}
