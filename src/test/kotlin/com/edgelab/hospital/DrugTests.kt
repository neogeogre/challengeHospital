package com.edgelab.hospital

import com.edgelab.hospital.entities.Drug
import com.edgelab.hospital.entities.Drug.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class DrugTests {

  @Test
  fun emptyDrug() {
    assertThat(Drug.of("")).isEqualTo(listOf<String>())
  }

  @Test
  fun parseAspirin() {
    assertThat(Drug.of("As")).containsExactly(ASPIRIN)
  }

  @Test
  fun parseAntibiotic() {
    assertThat(Drug.of("An")).containsExactly(ANTIBIOTIC)
  }

  @Test
  fun parseInsulin() {
    assertThat(Drug.of("I")).containsExactly(INSULIN)
  }

  @Test
  fun parseParacetamol() {
    assertThat(Drug.of("P")).containsExactly(PARACETAMOL)
  }

  @Test
  fun parseMultipleDrugs() {
    assertThat(Drug.of("As,An,I,P")).containsExactlyInAnyOrderElementsOf(listOf(ASPIRIN, ANTIBIOTIC, INSULIN, PARACETAMOL))
    assertThat(Drug.of("An,As,I,P")).containsExactlyInAnyOrderElementsOf(listOf(ASPIRIN, ANTIBIOTIC, INSULIN, PARACETAMOL))
  }
  
}