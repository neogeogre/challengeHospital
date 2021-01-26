package com.edgelab.hospital

import com.edgelab.hospital.State.*
import com.edgelab.hospital.State.Companion.of
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class StateTests {

  @Test
  fun emptyState() {
    assertThat(of("")).isEqualTo(listOf<String>())
  }

  @Test
  fun parseFever() {
    assertThat(of("F")).containsExactly(FEVER)
  }

  @Test
  fun parseHealthy() {
    assertThat(of("H")).containsExactly(HEALTHY)
  }

  @Test
  fun parseDiabetes() {
    assertThat(of("D")).containsExactly(DIABETES)
  }

  @Test
  fun parseTuberculosis() {
    assertThat(of("T")).containsExactly(TUBERCULOSIS)
  }

  @Test
  fun parseMultipleStates() {
    assertThat(of("F,H,D,T,X")).containsExactlyInAnyOrderElementsOf(listOf(FEVER, HEALTHY, DIABETES, TUBERCULOSIS, DEAD))
    assertThat(of("H,F,D,T,X")).containsExactlyInAnyOrderElementsOf(listOf(FEVER, HEALTHY, DIABETES, TUBERCULOSIS, DEAD))
  }
}
