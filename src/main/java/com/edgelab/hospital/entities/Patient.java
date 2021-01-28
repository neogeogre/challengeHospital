package com.edgelab.hospital.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiFunction;

/**
 * A patient of the {@link com.edgelab.hospital.entities.Hospital}
 * Can be treated only once per simulation
 */
@EqualsAndHashCode
@ToString
@Getter
public class Patient {

    private volatile State state;

    private final AtomicBoolean isTreated = new AtomicBoolean(false);

    public static Random RANDOM = new Random();

    private final BiFunction<List<Drug>, State, State> deathEffect = (drugs, state) -> {
        if (drugs.contains(Drug.PARACETAMOL) && drugs.contains(Drug.ASPIRIN)) return State.DEAD;
        if (!drugs.contains(Drug.INSULIN) && state.equals(State.DIABETES)) return State.DEAD;
        return state;
    };

    private final BiFunction<List<Drug>, State, State> sideEffect = (drugs, state) -> {
        if (drugs.contains(Drug.INSULIN) && drugs.contains(Drug.ANTIBIOTIC) && state.equals(State.HEALTHY))
            return State.FEVER;
        return state;
    };

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

    public Patient(State state) {
        this.state = state;
    }

    /**
     * Will ingest a list of {@link com.edgelab.hospital.entities.Drug} to the patient.
     *
     * @param drugs a list of {@link com.edgelab.hospital.entities.Drug}
     */
    public void treat(List<Drug> drugs) {
        List.of(this.deathEffect, this.sideEffect, this.cureEffect) // the order is important here
                .stream()
                .filter(effect -> !this.isTreated.get())
                .map(effect -> effect.apply(drugs, this.state))
                .filter(newState -> !newState.equals(this.state))
                .findFirst()
                .ifPresent(this::changePatientState);
        this.isTreated.set(false);
    }

    private void changePatientState(State newState) {
        this.state = newState;
        this.isTreated.set(true);
    }

}