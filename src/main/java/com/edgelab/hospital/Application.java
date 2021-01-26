package com.edgelab.hospital;

import com.edgelab.hospital.drugs.Drug;
import com.edgelab.hospital.drugs.Pharmacy;
import com.edgelab.hospital.states.States;

import java.util.List;
import java.util.stream.Collectors;

public class Application {

    public static void main(String[] main) {

        System.out.println("aaa");

        String patientStr = "F";
        String drugStr = "P";

        List<Drug> drugs = Pharmacy.pick(drugStr);
        List<Patient> patients = States.pick(patientStr)
                .stream().map(Patient::new)
                .collect(Collectors.toList());

        HospitalSimulator hospital = new HospitalSimulator(patients);
        hospital.simulate(drugs);
        String patientsResults = hospital.getResults();

    }

}
