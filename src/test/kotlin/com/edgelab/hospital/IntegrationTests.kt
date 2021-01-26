package com.edgelab.hospital

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatExceptionOfType
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.ByteArrayOutputStream
import java.io.PrintStream

class IntegrationTests {

  companion object {
    private var BAOS: ByteArrayOutputStream? = null
  }

  @BeforeEach
  fun redirectPrintOutStream() {
    BAOS = ByteArrayOutputStream()
    System.setOut(PrintStream(BAOS!!))
  }

  @Test
  fun invalidArguments() {
    assertThatExceptionOfType(IllegalArgumentException::class.java)
      .isThrownBy{ main(arrayOf("")) }
  }

  @Test
  fun emptyArguments() {
    main(arrayOf("", ""))
    assertThat(BAOS.toString().replace("([\\r\\n])".toRegex(), "")).isEqualTo("F:0,H:0,D:0,T:0,X:0")
  }

  @Test
  fun noPatientsMultiDrugs() {
    main(arrayOf("", "P,As"))
    assertThat(BAOS.toString().replace("([\\r\\n])".toRegex(), "")).isEqualTo("F:0,H:0,D:0,T:0,X:0")
  }

  @Test
  fun onePatient() {
    main(arrayOf("D", ""))
    assertThat(BAOS.toString().replace("([\\r\\n])".toRegex(), "")).isEqualTo("F:0,H:0,D:0,T:0,X:1")
  }

  @Test
  fun onePatientNoDrug() {
    main(arrayOf("F", ""))
    assertThat(BAOS.toString().replace("([\\r\\n])".toRegex(), "")).isEqualTo("F:1,H:0,D:0,T:0,X:0")
  }

  @Test
  fun onePatientOneDrug() {
    main(arrayOf("F", "P"))
    assertThat(BAOS.toString().replace("([\\r\\n])".toRegex(), "")).isEqualTo("F:0,H:1,D:0,T:0,X:0")
  }

  @Test
  fun multiPatientsNoDrugs() {
    main(arrayOf("D,D", ""))
    assertThat(BAOS.toString().replace("([\\r\\n])".toRegex(), "")).isEqualTo("F:0,H:0,D:0,T:0,X:2")
  }

  @Test
  fun multiPatientsMultiDrugs() {
    main(arrayOf("F,D,T,T", "P,An"))
    assertThat(BAOS.toString().replace("([\\r\\n])".toRegex(), "")).isEqualTo("F:0,H:3,D:0,T:0,X:1")
  }

}
