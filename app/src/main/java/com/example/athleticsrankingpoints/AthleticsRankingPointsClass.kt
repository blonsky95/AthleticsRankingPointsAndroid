package com.example.athleticsrankingpoints

import android.app.Application
import com.example.athleticsrankingpoints.di.databaseModule
import com.example.athleticsrankingpoints.di.reposModule
import com.example.athleticsrankingpoints.di.viewModelsModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class AthleticsRankingPointsClass : Application() {

  val koinModules = listOf(
    databaseModule,
    reposModule,
    viewModelsModule
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