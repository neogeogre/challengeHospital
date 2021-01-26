package com.edgelab.hospital.entities

enum class State(val code: String) {

  FEVER("F"), HEALTHY("H"), DIABETES("D"), TUBERCULOSIS("T"), DEAD("X");

  companion object {

    fun of(input: String) = input
      .split(",")
      .filter { it.isNotEmpty() }
      .map { values().first { state -> state.code == it } }

  }

}
