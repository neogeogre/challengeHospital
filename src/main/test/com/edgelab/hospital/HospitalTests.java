package com.edgelab.hospital;

import com.edgelab.hospital.entities.Drug;
import com.edgelab.hospital.entities.Hospital;
import com.edgelab.hospital.entities.Patient;
import com.edgelab.hospital.entities.State;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class HospitalTests {

    @Test
    void checkHospitalReportGeneration() {
        Patient patient1 = Mockito.mock(Patient.class);
        Patient patient2 = Mockito.mock(Patient.class);
        Patient patient3 = Mockito.mock(Patient.class);

        when(patient1.getState()).thenReturn(State.FEVER);
        when(patient2.getState()).thenReturn(State.HEALTHY);
        when(patient3.getState()).thenReturn(State.TUBERCULOSIS);

        Hospital hospital = new Hospital(Arrays.asList(patient1, patient2, patient3));
        String results = hospital.runSimulation(Arrays.asList(Drug.ANTIBIOTIC, Drug.ASPIRIN));
        assertEquals("F:1,H:1,D:0,T:1,X:0", results);
    }

    @Test
    void multiSimulations() {
        List<Patient> patients = Arrays.asList(new Patient(State.FEVER), new Patient(State.DIABETES), new Patient(State.TUBERCULOSIS));
        Hospital hospital = new Hospital(patients);

        String results1 = hospital.runSimulation(Arrays.asList(Drug.PARACETAMOL, Drug.INSULIN));
        assertEquals("F:0,H:1,D:1,T:1,X:0", results1);

        String results2 = hospital.runSimulation(Arrays.asList(Drug.ANTIBIOTIC, Drug.ASPIRIN));
        assertEquals("F:0,H:2,D:0,T:0,X:1", results2);
    }

}
