package com.edgelab.hospital;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IntegrationTests {

    private static ByteArrayOutputStream BAOS;

    @BeforeEach
    void redirectPrintOutStream() {
        BAOS = new ByteArrayOutputStream();
        PrintStream redirectOut = new PrintStream(BAOS);
        System.setOut(redirectOut);
    }

    @Test
    void noPatientsNoDrugs() {
        Application.main(new String[]{"", ""});
        assertEquals("F:0,H:0,D:0,T:0,X:0", BAOS.toString().replaceAll("([\\r\\n])", ""));
    }

    @Test
    void noPatientsMultiDrugs() {
        Application.main(new String[]{"", "P,As"});
        assertEquals("F:0,H:0,D:0,T:0,X:0", BAOS.toString().replaceAll("([\\r\\n])", ""));
    }

    @Test
    void onePatientNoDrug() {
        Application.main(new String[]{"F", ""});
        assertEquals("F:1,H:0,D:0,T:0,X:0", BAOS.toString().replaceAll("([\\r\\n])", ""));
    }

    @Test
    void onePatientOneDrug() {
        Application.main(new String[]{"F", "P"});
        assertEquals("F:0,H:1,D:0,T:0,X:0", BAOS.toString().replaceAll("([\\r\\n])", ""));
    }

    @Test
    void multiPatientsNoDrugs() {
        Application.main(new String[]{"D,D", ""});
        assertEquals("F:0,H:0,D:0,T:0,X:2", BAOS.toString().replaceAll("([\\r\\n])", ""));
    }

    @Test
    void multiPatientsMultiDrugs() {
        Application.main(new String[]{"F,D,T,T", "P,An"});
        assertEquals("F:0,H:3,D:0,T:0,X:1", BAOS.toString().replaceAll("([\\r\\n])", ""));
    }

}
