package com.github.rougsig.rxflux.processor

import com.github.rougsig.rxflux.annotations.FluxState
import com.github.rougsig.rxflux.processor.base.Generator
import com.github.rougsig.rxflux.processor.stategenerator.StateType
import com.github.rougsig.rxflux.processor.stategenerator.stateGenerator
import com.google.auto.service.AutoService
import me.eugeniomarletti.kotlin.processing.KotlinAbstractProcessor
import java.io.File
import javax.annotation.processing.Processor
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement

private const val OPTION_GENERATED = "rxflux.generated"

@AutoService(Processor::class)
class RxFluxProcessor : KotlinAbstractProcessor() {
  private val fluxStateAnnotationClass = FluxState::class.java

  override fun getSupportedAnnotationTypes(): Set<String> = setOf(fluxStateAnnotationClass.canonicalName)

  override fun getSupportedSourceVersion(): SourceVersion = SourceVersion.latest()

  override fun getSupportedOptions() = setOf(OPTION_GENERATED)

  override fun process(annotations: Set<TypeElement>, roundEnv: RoundEnvironment): Boolean {
    for (type in roundEnv.getElementsAnnotatedWith(fluxStateAnnotationClass)) {
      val stateType = StateType.get(this, type) ?: continue
      stateGenerator.generateAndWrite(stateType)
    }
    return true
  }

  private fun <T> Generator<T>.generateAndWrite(type: T) {
    val fileSpec = generateFile(type)

    val outputDirPath = "$generatedDir/${fileSpec.packageName.replace(".", "/")}"
    val outputDir = File(outputDirPath).also { it.mkdirs() }

    val file = File(outputDir, "${fileSpec.name}.kt")
    file.writeText(fileSpec.toString())
  }
}
