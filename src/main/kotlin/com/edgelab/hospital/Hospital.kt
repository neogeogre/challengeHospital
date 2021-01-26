package com.edgelab.hospital

class Hospital(private val drugs: List<Drug>) {

  fun runSimulation(patients: List<Patient>) = patients.onEach { it.treat(drugs) }

}
