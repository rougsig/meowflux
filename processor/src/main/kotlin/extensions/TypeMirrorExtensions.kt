package com.github.rougsig.rxflux.processor.extensions

import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.asTypeName
import javax.lang.model.type.TypeMirror

internal fun TypeMirror.asTypeName(): TypeName {
  return this.asTypeName()
}
