package com.edgelab.hospital.states;

import com.edgelab.hospital.drugs.Drug;

public class Dead implements State {

    @Override
    public State applyDrug(Drug drug) {
        return null;
    }
}
