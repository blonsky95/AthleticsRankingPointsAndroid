package com.tatoeapps.athleticsrankingpoints

import android.content.Context
import androidx.annotation.StringRes

fun String.toIntOrZero():Int {
  return toIntOrNull() ?: 0
}

fun String.makeZeroIfEmpty():String {
  return this.ifEmpty {
    "0"
  }
}

fun String.upperCaseFirstLetter():String {
  return "${this.substring(0,1).uppercase()}${this.substring(1,this.length)}"
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

fun Context.getResString(@StringRes stringRes: Int) = this.resources.getString(stringRes)