package com.edgelab.hospital;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IntegrationTests {

    @Test
    void noPatientsNoDrugs() {
        assertEquals("F:0,H:0,D:0,T:0,X:0", Application.run("", ""));
    }

    @Test
    void noPatientsMultiDrugs() {
        assertEquals("F:0,H:0,D:0,T:0,X:0", Application.run("", "P,As"));
    }

    @Test
    void onePatientNoDrug() {
        assertEquals("F:1,H:0,D:0,T:0,X:0", Application.run("F", ""));
    }

    @Test
    void onePatientOneDrug() {
        assertEquals("F:0,H:1,D:0,T:0,X:0", Application.run("F", "P"));
    }

    @Test
    void multiPatientsNoDrugs() {
        assertEquals("F:0,H:0,D:0,T:0,X:2", Application.run("D,D", ""));
    }

    @Test
    void multiPatientsMultiDrugs() {
        assertEquals("F:0,H:3,D:0,T:0,X:1", Application.run("F,D,T,T", "P,An"));
    }

}
