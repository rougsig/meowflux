package com.github.rougsig.rxflux.processor

class StateGeneratorTest : APTest("com.example.test") {
  fun testLoginState() {
    testProcessor(
        AnnotationProcessor(
            sourceFiles = listOf("LoginState.java"),
            destinationFile = "LoginFluxState.kt.txt",
            processor = RxFluxProcessor()
        ),
        actualFileLocation = { "${it.path}/generated" }
    )
  }

  fun testAnimalState() {
    testProcessor(
      AnnotationProcessor(
        sourceFiles = listOf("DuckState.java", "CatState.java", "AnimalState.java"),
        destinationFile = "AnimalFluxState.kt.txt",
        processor = RxFluxProcessor()
      ),
      actualFileLocation = { "${it.path}/generated" }
    )
  }
}
