package com.edgelab.hospital.api;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiFunction;

/**
 * A patient of the {@link com.edgelab.hospital.api.Hospital}
 * Can be treated only once
 */
public class Patient {

    private volatile State state;

    private final AtomicBoolean treated = new AtomicBoolean(false);

    public Patient(State disease) {
        this.state = disease;
    }

    public State getState() {
        return state;
    }

    public void treat(List<Drug> drugs, BiFunction<List<Drug>, State, State> drugEffect) {
        if (!this.treated.get()) {
            State newState = drugEffect.apply(drugs, this.state);
            if (!newState.equals(this.state)) {
                this.state = newState;
                this.treated.set(true);
            }
        }
    }

}
