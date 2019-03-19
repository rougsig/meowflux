package com.github.rougsig.rxflux.core

interface FluxState<S : Any> {
  interface State<F, S : Any> {
    val FIELDS: F
    val ALL_FIELDS: Set<Field<*, S>>
  }

  class Field<T, S : Any>(
      val name: String,
      private val type: String,
      private val state: String
  ) {
    override fun toString(): String {
      return "CreateFluxState.Field(name=$name, type=$type, state=$state)"
    }

    override fun equals(other: Any?): Boolean {
      return this === other
    }

    override fun hashCode(): Int {
      return name.hashCode()
    }
  }

  val ALL_FIELDS: Set<Field<*, S>>

  fun <T : Any?> setField(field: FluxState.Field<T, S>, value: T): S

  fun <T: Any?> getField(field: FluxState.Field<T, S>): T
}
