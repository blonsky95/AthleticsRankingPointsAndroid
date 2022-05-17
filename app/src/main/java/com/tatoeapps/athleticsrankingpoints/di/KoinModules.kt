package com.tatoeapps.athleticsrankingpoints.di

import androidx.room.Room
import com.tatoeapps.athleticsrankingpoints.data.database.RankingScoreDatabase
import com.tatoeapps.athleticsrankingpoints.data.entities.RankingScorePerformanceData
import com.tatoeapps.athleticsrankingpoints.data.repositories.AthleticsEventsRepositoryImpl
import com.tatoeapps.athleticsrankingpoints.data.repositories.EventGroupsRepositoryImpl
import com.tatoeapps.athleticsrankingpoints.data.repositories.RankingScorePerformanceRepositoryImpl
import com.tatoeapps.athleticsrankingpoints.domain.interfaces.AthleticsEventsRepository
import com.tatoeapps.athleticsrankingpoints.domain.interfaces.EventGroupsRepository
import com.tatoeapps.athleticsrankingpoints.domain.interfaces.RankingScorePerformanceRepository
import com.tatoeapps.athleticsrankingpoints.presentation.screens.homescreen.HomeViewModel
import com.tatoeapps.athleticsrankingpoints.presentation.screens.savedscreen.PerformancesViewModel
import com.tatoeapps.athleticsrankingpoints.presentation.screens.calculatorscreen.EventGroupSelectorViewModel
import com.tatoeapps.athleticsrankingpoints.presentation.screens.calculatorscreen.EventGroupSimulatorViewModel
import com.tatoeapps.athleticsrankingpoints.presentation.screens.splashscreen.SplashViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


const val allAthleticsEventsJsonFileName = "all_events.json"
const val allEventGroupsJsonFileName = "event_groups.json"
const val DATABASE_NAME = "ranking_score_database"



val databaseModule = module {
  single {
    Room.databaseBuilder(androidContext(), RankingScoreDatabase::class.java, DATABASE_NAME)
//      .fallbackToDestructiveMigration()
//      .addMigrations(MIGRATION_1_2)
      .build()
  }
  single {get<RankingScoreDatabase>().rankingScoreDatabaseDao()}
}

val cachesModule = module {
  singleCache<RankingScorePerformanceData>()
}

val reposModule = module {
  single <AthleticsEventsRepository> {
    AthleticsEventsRepositoryImpl(
      applicationContext = androidContext(),
      get()
    )
  }

  single <EventGroupsRepository> {
    EventGroupsRepositoryImpl(
      applicationContext = androidContext(),
      get()
    )
  }

  single <RankingScorePerformanceRepository>{
    RankingScorePerformanceRepositoryImpl(
      rankingScoreDatabaseDao = get(),
      cache = getCache()
    )
  }
}

val viewModelsModule = module {
  viewModel { HomeViewModel(get()) }

  viewModel { EventGroupSelectorViewModel(get()) }

  viewModel { params ->
    EventGroupSimulatorViewModel(get(), simulatorDTO = params.get(), get()) }

  viewModel { PerformancesViewModel(get()) }

  viewModel { SplashViewModel(get(), get()) }

}

val roomTestModule = module {
  single {
    // In-Memory database config
    Room.inMemoryDatabaseBuilder(androidContext(), RankingScoreDatabase::class.java)
      .allowMainThreadQueries()
      .build()
  }
}



