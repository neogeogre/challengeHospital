package com.edgelab.hospital;

import java.util.List;
import java.util.stream.Collectors;

public class Application {

    public static void main(String[] main) {
        String stateStr = main[0];
        String drugStr = "";
        if (main.length > 1) {
            drugStr = main[1];
        }
        String results = run(stateStr, drugStr);
        System.out.println(results);
    }

    public static String run(String statesString, String drugsString) {
        List<Drug> drugs = Drug.parseDrugs(drugsString);

        List<Patient> patients = State.parseStates(statesString)
                .stream().map(Patient::new)
                .collect(Collectors.toList());

        HospitalSimulator hospital = new HospitalSimulator(patients);
        return hospital.runSimulation(drugs);
    }

}
