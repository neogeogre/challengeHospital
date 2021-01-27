package com.edgelab.hospital;

import java.util.stream.Collectors;

public class Application {

    public static void main(String[] main) {
        var stateStr = main[0];
        var drugStr = "";
        if (main.length > 1) {
            drugStr = main[1];
        }
        String result = getResult(stateStr, drugStr);
        System.out.println(result);
    }

    public static String getResult(String stateStr, String drugStr) {
        var drugs = Drug.parse(drugStr);
        var patients = State.parse(stateStr)
                .stream().map(Patient::new)
                .collect(Collectors.toList());

        var hospital = new HospitalSimulator(patients);
        hospital.simulate(drugs);
        return hospital.getResults();
    }

}
