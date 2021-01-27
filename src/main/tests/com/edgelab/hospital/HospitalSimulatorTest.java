package com.edgelab.hospital;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class HospitalSimulatorTest {

    @Test
    void test1() {
        var stateStr = "D,D";
        var drugStr = "";
        String result = Application.getResult(stateStr, drugStr);
        Assertions.assertEquals(result, "F:0,H:0,D:0,T:0,X:2");
    }

    @Test
    void test2() {
        var stateStr = "F";
        var drugStr = "P";
        String result = Application.getResult(stateStr, drugStr);
        Assertions.assertEquals(result, "F:0,H:1,D:0,T:0,X:0");
    }

}