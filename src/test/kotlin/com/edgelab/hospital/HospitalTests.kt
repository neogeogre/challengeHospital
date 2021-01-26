package com.edgelab.hospital

import com.edgelab.hospital.Drug.*
import com.edgelab.hospital.State.*
import io.mockk.every
import io.mockk.spyk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class HospitalTests {

  @Test
  fun checkHospitalReportGeneration() {
    val patient1 = spyk(Patient(HEALTHY))
    val patient2 = spyk(Patient(HEALTHY))
    val patient3 = spyk(Patient(HEALTHY))

    every { patient1.state } returns FEVER
    every { patient2.state } returns HEALTHY
    every { patient3.state } returns TUBERCULOSIS

    val results = Hospital(listOf(ANTIBIOTIC, ASPIRIN)).runSimulation(listOf(patient1, patient2, patient3))
    assertThat(results.map { it.state }).containsExactlyInAnyOrderElementsOf(listOf(FEVER, HEALTHY, TUBERCULOSIS))
  }

  @Test
  fun multiSimulations() {
    val patients = listOf(Patient(FEVER), Patient(DIABETES), Patient(TUBERCULOSIS))
    assertThat(Hospital(listOf(PARACETAMOL, INSULIN)).runSimulation(patients).map { it.state })
      .containsExactlyInAnyOrderElementsOf(listOf(HEALTHY, DIABETES, TUBERCULOSIS))
    assertThat(Hospital(listOf(ANTIBIOTIC, ASPIRIN)).runSimulation(patients).map { it.state })
      .containsExactlyInAnyOrderElementsOf(listOf(HEALTHY, HEALTHY, DEAD))
  }

}
