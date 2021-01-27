package com.edgelab.hospital;

import java.util.List;
import java.util.function.BiFunction;

public class Patient {

    private State state;

    private boolean treated = false;

    public Patient(State disease) {
        this.state = disease;
    }

    public State getState() {
        return state;
    }

    void treat(List<Drug> drugs, BiFunction<List<Drug>, State, State> drugEffect) {
        if (this.treated) return;
        var newState = drugEffect.apply(drugs, this.state);
        if (!newState.equals(this.state)) {
            this.state = newState;
            this.treated = true;
        }
    }

}
