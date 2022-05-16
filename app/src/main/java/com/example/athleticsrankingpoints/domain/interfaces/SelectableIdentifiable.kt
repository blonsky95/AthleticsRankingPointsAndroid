package com.example.athleticsrankingpoints.domain.interfaces

import android.content.Context

interface SelectableIdentifiable {
  val id: String
  fun getReadableText(context: Context? = null):String
}

inline fun <reified T> findById(id: String): T where T : Enum<T>, T : SelectableIdentifiable {
  return enumValues<T>().find { it.id == id }!!
}