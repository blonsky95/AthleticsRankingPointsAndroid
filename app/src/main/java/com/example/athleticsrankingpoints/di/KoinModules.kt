package com.example.athleticsrankingpoints.di

import androidx.room.Room
import com.example.athleticsrankingpoints.data.database.RankingScoreDatabase
import com.example.athleticsrankingpoints.domain.interfaces.AthleticsEventsRepository
import com.example.athleticsrankingpoints.domain.interfaces.EventGroupsRepository
import com.example.athleticsrankingpoints.domain.interfaces.RankingScorePerformanceRepository
import com.example.athleticsrankingpoints.presentation.screens.lookupscreen.LookUpViewModel
import com.example.athleticsrankingpoints.presentation.screens.performancesscreen.PerformancesViewModel
import com.example.athleticsrankingpoints.presentation.screens.simulatorscreen.EventGroupSelectorViewModel
import com.example.athleticsrankingpoints.presentation.screens.simulatorscreen.EventGroupSimulatorViewModel
import com.example.athleticsrankingpoints.presentation.screens.splashscreen.SplashViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


const val allEventsJsonFileName = "all_events.json"
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


val reposModule = module {
  single <AthleticsEventsRepository> {
    com.example.athleticsrankingpoints.data.repositories.AthleticsEventsRepository(
      applicationContext = androidContext(),
      get()
    )
  }

  single <EventGroupsRepository> {
    com.example.athleticsrankingpoints.data.repositories.EventGroupsRepository(
      applicationContext = androidContext(),
      jsonFileName = allEventGroupsJsonFileName
    )
  }

  single <RankingScorePerformanceRepository>{
    com.example.athleticsrankingpoints.data.repositories.RankingScorePerformanceRepository(
      rankingScoreDatabaseDao = get()
    )
  }

}

val viewModelsModule = module {
  viewModel { LookUpViewModel(get()) }

  viewModel { EventGroupSelectorViewModel(get()) }

  viewModel { params ->
    EventGroupSimulatorViewModel(get(), simulatorDataModel = params.get(), get()) }

  viewModel { PerformancesViewModel(get()) }

  viewModel { SplashViewModel(get()) }

}

val roomTestModule = module {
  single {
    // In-Memory database config
    Room.inMemoryDatabaseBuilder(androidContext(), RankingScoreDatabase::class.java)
      .allowMainThreadQueries()
      .build()
  }
}

