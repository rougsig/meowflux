package com.github.rougsig.rxflux.processor.stategenerator

import com.github.rougsig.rxflux.processor.extensions.beginWithLowerCase
import com.github.rougsig.rxflux.processor.extensions.javaToKotlinType
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.asTypeName
import me.eugeniomarletti.kotlin.processing.KotlinProcessingEnvironment
import org.jetbrains.annotations.Nullable
import javax.lang.model.element.ExecutableElement

internal data class FieldType(
  val name: String,
  val type: TypeName
) {
  companion object {
    fun get(env: KotlinProcessingEnvironment, el: ExecutableElement, states: Set<TypeName>): FieldType {
      val isNullable = el.getAnnotation(Nullable::class.java) != null

      val type = states.find { it == el.returnType.asTypeName() }?.let {
        ClassName.bestGuess(StateType.generateStateName((it as ClassName).simpleName))
      } ?: el.returnType.asTypeName().javaToKotlinType().copy(nullable = isNullable)


      return FieldType(
        name = el.simpleName.toString().removePrefix("get").beginWithLowerCase(),
        type = type
      )
    }
  }
}
