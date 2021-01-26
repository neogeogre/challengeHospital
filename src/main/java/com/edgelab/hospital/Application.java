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

        List<Drug> drugs = Drug.parse(drugStr);

        List<Patient> patients = State.parse(stateStr)
                .stream().map(Patient::new)
                .collect(Collectors.toList());

        HospitalSimulator hospital = new HospitalSimulator(patients);
        hospital.simulate(drugs);
        System.out.println(hospital.getResults());
    }

}
