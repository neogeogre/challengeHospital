package com.edgelab.hospital

class Patient(var state: State) {

    private var isTreated: Boolean = false

    fun treat(drugs: List<Drug>) {
        Effect.values() // the order is important here
            .onEach { effect -> effect.drugs = drugs }
            .filter { !isTreated }
            .map { effect -> effect.apply(state) }
            .firstOrNull { newState -> newState != state }
            ?.also { newState -> state = newState; isTreated = true }
        isTreated = false
    }

}
