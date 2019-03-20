package com.github.rougsig.rxflux.processor.stategenerator

import com.github.rougsig.rxflux.processor.extensions.className
import com.github.rougsig.rxflux.processor.extensions.enclosedMethods
import com.github.rougsig.rxflux.processor.extensions.error
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.TypeVariableName
import me.eugeniomarletti.kotlin.metadata.KotlinClassMetadata
import me.eugeniomarletti.kotlin.metadata.kotlinMetadata
import me.eugeniomarletti.kotlin.processing.KotlinProcessingEnvironment
import javax.lang.model.element.Element
import javax.lang.model.element.TypeElement

internal data class StateType(
  val fields: List<FieldType>,
  val stateElement: TypeElement,
  val stateType: TypeVariableName,
  val packageName: String
) {
  companion object {
    fun get(env: KotlinProcessingEnvironment, el: Element, states: Set<TypeName>): StateType? {
      val typeMetadata = el.kotlinMetadata
      if (el !is TypeElement || typeMetadata !is KotlinClassMetadata) {
        env.error("@CreateFluxState can't be applied to $el: must be kotlin class", el)
        return null
      }

      val fields = el.enclosedMethods.map { FieldType.get(env, it, states) }

      return StateType(
        fields = fields,
        stateElement = el,
        stateType = TypeVariableName(generateStateName(el.className.simpleName)),
        packageName = "${el.className.packageName}.generated"
      )
    }

    fun generateStateName(name: String): String {
      return if (name.contains("State")) {
        name.replace("State", "FluxState")
      } else {
        "${name}FluxState"
      }
    }
  }
}
