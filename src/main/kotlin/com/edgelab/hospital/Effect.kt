package com.edgelab.hospital

import com.edgelab.hospital.Drug.*
import com.edgelab.hospital.State.*
import kotlin.random.Random


enum class Effect(var drugs: List<Drug>) {

  DEATH(emptyList()) {
    override fun apply(state: State) = when {
      checkDead(state) -> DEAD
      else -> state
    }
  },

  SIDE(emptyList()) {
    override fun apply(state: State) = when {
      checkSideEffect(state) -> FEVER
      else -> state
    }
  },

  CURE(emptyList()) {
    override fun apply(state: State) = when {
      checkCured(state) -> HEALTHY
      else -> state
    }
  };

  abstract fun apply(state: State): State

  fun checkDead(state: State) = deathByDrugMix() || deathByDiabetes(state)

  fun checkSideEffect(state: State) = sideEffectByDrugMix(state)

  fun checkCured(state: State)
  = isFeverCured1(state) || isFeverCured2(state) || isTuberculosisCured(state) || isResurrected(state)

  private fun deathByDiabetes(state: State) = !drugs.contains(INSULIN) && state == DIABETES

  private fun deathByDrugMix() = drugs.contains(PARACETAMOL) && drugs.contains(ASPIRIN)

  private fun sideEffectByDrugMix(state: State) =  drugs.contains(INSULIN) && drugs.contains(ANTIBIOTIC) && state == HEALTHY

  private fun isFeverCured1(state: State) = drugs.contains(ASPIRIN) && state == FEVER

  private fun isFeverCured2(state: State) = drugs.contains(PARACETAMOL) && state == FEVER

  private fun isTuberculosisCured(state: State) = drugs.contains(ANTIBIOTIC) && state == TUBERCULOSIS

  private fun isResurrected(state: State)  = state == DEAD && random.nextDouble() <= RESURRECTION_RATIO

  companion object {
    var random = Random
    const val RESURRECTION_RATIO = 0.000001
  }

}
