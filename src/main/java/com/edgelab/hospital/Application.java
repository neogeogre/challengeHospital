package com.edgelab.hospital;

import com.edgelab.hospital.entities.Drug;
import com.edgelab.hospital.entities.Hospital;
import com.edgelab.hospital.entities.Patient;
import com.edgelab.hospital.entities.State;

import java.util.List;
import java.util.stream.Collectors;

public class Application {

    public static void main(String[] args) {
        if (args == null || args.length == 0) {
            args = new String[]{""};
        }
        if (args.length == 1) {
            args = new String[]{args[0], ""};
        }
        System.out.println(run(args[0], args[1]));
    }

    private static String run(String statesString, String drugsString) {
        List<Drug> drugs = Drug.parseDrugs(drugsString);

        List<Patient> patients = State.parseStates(statesString)
                .stream().map(Patient::new)
                .collect(Collectors.toList());

        Hospital hospital = new Hospital(patients);
        return hospital.runSimulation(drugs);
    }

}
