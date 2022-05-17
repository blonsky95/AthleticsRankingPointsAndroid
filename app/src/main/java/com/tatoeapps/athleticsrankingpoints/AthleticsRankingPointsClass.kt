package com.tatoeapps.athleticsrankingpoints

import android.app.Application
import com.tatoeapps.athleticsrankingpoints.di.cachesModule
import com.tatoeapps.athleticsrankingpoints.di.databaseModule
import com.tatoeapps.athleticsrankingpoints.di.reposModule
import com.tatoeapps.athleticsrankingpoints.di.viewModelsModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class AthleticsRankingPointsClass : Application() {

  val koinModules = listOf(
    databaseModule,
    cachesModule,
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