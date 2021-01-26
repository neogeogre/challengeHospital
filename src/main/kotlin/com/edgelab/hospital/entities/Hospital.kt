package com.edgelab.hospital.entities

class Hospital(private val drugs: List<Drug>) {

  fun runSimulation(patients: List<Patient>) = patients.onEach { it.treat(drugs) }

}
