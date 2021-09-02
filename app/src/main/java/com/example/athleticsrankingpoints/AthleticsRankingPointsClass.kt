package com.example.athleticsrankingpoints

import android.app.Application
import com.example.athleticsrankingpoints.di.firstModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class AthleticsRankingPointsClass : Application() {

  val koinModules = listOf(
    firstModule
  )

  init {
    initKoin()
  }

  private fun initKoin() {
    startKoin {
      androidContext(this@AthleticsRankingPointsClass)
      modules(koinModules)
    }
  }



}