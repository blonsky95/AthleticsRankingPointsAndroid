package com.example.athleticsrankingpoints.domain.interfaces

interface SelectableIdentifiable {
  val id: String
  fun getReadableText():String
}

inline fun <reified T> findById(id: String): T where T : Enum<T>, T : SelectableIdentifiable {
  return enumValues<T>().find { it.id == id }!!
}