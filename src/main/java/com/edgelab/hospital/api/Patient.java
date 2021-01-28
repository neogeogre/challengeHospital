package com.edgelab.hospital.api;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiFunction;

/**
 * A patient of the {@link com.edgelab.hospital.api.Hospital}
 * Can be treated only once
 */
public class Patient {

    private volatile State state;

    private final AtomicBoolean isTreated = new AtomicBoolean(false);

    public static Random RANDOM = new Random();

    /**
     * A BiFunction for the conditions that will cause death to a patient
     */
    private final BiFunction<List<Drug>, State, State> deathEffect = (drugs, state) -> {
        if (drugs.contains(Drug.PARACETAMOL) && drugs.contains(Drug.ASPIRIN)) return State.DEAD;
        if (!drugs.contains(Drug.INSULIN) && state.equals(State.DIABETES)) return State.DEAD;
        return state;
    };

    /**
     * A BiFunction for the conditions that will cause side effects to a patient
     */
    private final BiFunction<List<Drug>, State, State> sideEffect = (drugs, state) -> {
        if (drugs.contains(Drug.INSULIN) && drugs.contains(Drug.ANTIBIOTIC) && state.equals(State.HEALTHY)) return State.FEVER;
        return state;
    };

    /**
     * A BiFunction for the conditions to cure a patient
     */
    private final BiFunction<List<Drug>, State, State> cureEffect = (drugs, state) -> {
        if (drugs.contains(Drug.ASPIRIN) && state.equals(State.FEVER)) return State.HEALTHY;
        if (drugs.contains(Drug.PARACETAMOL) && state.equals(State.FEVER)) return State.HEALTHY;
        if (drugs.contains(Drug.ANTIBIOTIC) && state.equals(State.TUBERCULOSIS)) return State.HEALTHY;
        if (state.equals(State.DEAD)) {
            if (RANDOM.nextDouble() <= 0.000001) {
                return State.HEALTHY;
            }
        }
        return state;
    };

    public Patient(State disease) {
        this.state = disease;
    }

    public State getState() {
        return state;
    }

    public void treat(List<Drug> drugs) {
        if (!this.isTreated.get()) {
            List.of(this.deathEffect, this.sideEffect, this.cureEffect) // the order is important here
                    .stream()
                    .map(effect -> effect.apply(drugs, this.state))
                    .forEach(this::checkStateChange);
        }
    }

    private void checkStateChange(State newState) {
        if (!newState.equals(this.state)) {
            this.state = newState;
            this.isTreated.set(true);
        }
    }

}
