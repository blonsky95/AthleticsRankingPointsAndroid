package com.example.athleticsrankingpoints

fun String.toIntOrZero():Int {
  return toIntOrNull() ?: 0
}

fun String.makeZeroIfEmpty():String {
  return if (this.isEmpty()) {
    "0"
  } else {
    this
  }
}

/**
 * Returns ints (0 if not possible)
 */
fun List<String>.toIntsArray() : List<Int> {
  val intArrayList = mutableListOf<Int>()
  for (string in this) {
    intArrayList.add(string.toIntOrZero())
  }
  return intArrayList
}