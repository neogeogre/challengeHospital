package com.edgelab.hospital.states;

import com.edgelab.hospital.drugs.Drug;

public interface State {

    State applyDrug(Drug drug);

}
