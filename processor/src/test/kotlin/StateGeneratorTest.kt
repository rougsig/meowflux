package com.github.rougsig.mvifake.processor

import com.github.rougsig.rxflux.processor.RxFluxProcessor

class StateGeneratorTest : APTest("com.example.test") {
  fun testLoginState() {
    testProcessor(AnnotationProcessor(
      sourceFiles = listOf("LoginState.java"),
      destinationFile = "LoginState.kt.txt",
      processor = RxFluxProcessor()
    ))
  }
}
