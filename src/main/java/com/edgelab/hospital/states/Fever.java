package com.edgelab.hospital.states;

import com.edgelab.hospital.drugs.Aspirin;
import com.edgelab.hospital.drugs.Drug;

public class Fever implements State {

    @Override
    public State applyDrug(Drug drug) {
        if (drug instanceof Aspirin) return new Healthy();
        return this;
    }

}