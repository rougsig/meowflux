package com.github.rougsig.rxflux.core

interface FluxState<S> {
  interface State<F, S> {
    val FIELDS: F
    val ALL_FIELDS: Set<Field<*, S>>
  }

  class Field<T, S>(
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

  fun <T : Any?> setField(field: FluxState.Field<T, S>, value: T): S

  fun <T: Any?> getField(field: FluxState.Field<T, S>): T
}
