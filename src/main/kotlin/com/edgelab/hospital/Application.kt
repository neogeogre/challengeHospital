package com.edgelab.hospital

fun main(args: Array<String>) {
  if (args.size != 2) throw IllegalArgumentException("Needs exactly two arguments: list of states and list of drugs")
  val patients = State.of(args[0]).map { Patient(it) }
  val drugs = Drug.of(args[1])
  val treatedPatients = Hospital(drugs).runSimulation(patients)
  val report = buildReport(treatedPatients)
  println(report)
}

private fun buildReport(patients: List<Patient>): String = State.values()
  .joinToString(",") { stateToPrint ->
    stateToPrint.code + ":" + patients.count { patient -> stateToPrint == patient.state }
  }
