package com.edgelab.hospital;

import com.edgelab.hospital.drugs.Drug;
import com.edgelab.hospital.states.Dead;
import com.edgelab.hospital.states.Healthy;
import com.edgelab.hospital.states.State;

import java.util.List;

public class Patient {

    private State state;

    private List<Drug> drugsIngested;

    private boolean treated = false;

    public Patient(State state) {
        this.state = state;
    }

    void treat(List<Drug> drugs) {
        if (this.treated) return;
        if ((state.getClass().equals(Dead.class))) {

            if (Math.random() < 0.000001){
                this.state = new Healthy();
            }

            return;
        }
        drugs.forEach(drug -> this.state = state.applyDrug(drug));
        this.treated = true;
    }

}
