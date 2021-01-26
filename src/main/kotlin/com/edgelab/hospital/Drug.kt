package com.edgelab.hospital

enum class Drug(val code: String) {

  ASPIRIN("As"), ANTIBIOTIC("An"), INSULIN("I"), PARACETAMOL("P");

  companion object {

    fun of(input: String) = input
      .split(",")
      .filter { it.isNotEmpty() }
      .map { values().first { drug -> drug.code == it } }

  }
}
