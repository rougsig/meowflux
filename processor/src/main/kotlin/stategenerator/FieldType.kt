package com.github.rougsig.rxflux.processor.stategenerator

import com.github.rougsig.rxflux.processor.extensions.asClassName
import com.github.rougsig.rxflux.processor.extensions.beginWithLowerCase
import com.github.rougsig.rxflux.processor.extensions.javaToKotlinType
import com.squareup.kotlinpoet.TypeName
import me.eugeniomarletti.kotlin.processing.KotlinProcessingEnvironment
import org.jetbrains.annotations.Nullable
import javax.lang.model.element.ExecutableElement

internal data class FieldType(
  val name: String,
  val type: TypeName
) {
  companion object {
    fun get(env: KotlinProcessingEnvironment, el: ExecutableElement): FieldType {
      val isNullable = el.getAnnotation(Nullable::class.java) != null
      return FieldType(
        name = el.simpleName.toString().removePrefix("get").beginWithLowerCase(),
        type = el.returnType.asClassName().javaToKotlinType().copy(nullable = isNullable)
      )
    }
  }
}
