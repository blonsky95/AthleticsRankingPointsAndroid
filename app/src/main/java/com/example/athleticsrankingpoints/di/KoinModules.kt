package com.example.athleticsrankingpoints.di

import androidx.room.Room
import com.example.athleticsrankingpoints.data.*
import com.example.athleticsrankingpoints.presentation.screens.simulatorscreen.EventGroupSelectorViewModel
import com.example.athleticsrankingpoints.presentation.screens.simulatorscreen.EventGroupSimulatorViewModel
import com.example.athleticsrankingpoints.presentation.screens.lookupscreen.LookUpViewModel
import org.koin.dsl.module
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import com.example.athleticsrankingpoints.domain.interfaces.AthleticsEventsRepository
import com.example.athleticsrankingpoints.domain.interfaces.EventGroupsRepository
import com.example.athleticsrankingpoints.domain.interfaces.RankingScorePerformanceRepository
import com.example.athleticsrankingpoints.presentation.screens.performancesscreen.PerformancesViewModel


const val allEventsJsonFileName = "all_events.json"
const val allEventGroupsJsonFileName = "event_groups.json"
const val DATABASE_NAME = "ranking_score_database"



val databaseModule = module {
  single <RankingScoreDatabase>{
    Room.databaseBuilder(androidContext(), RankingScoreDatabase::class.java, DATABASE_NAME)
      .fallbackToDestructiveMigration()
      .build()
  }
  single <RankingScoreDatabaseDao>{get<RankingScoreDatabase>().rankingScoreDatabaseDao()}
}


val reposModule = module {
  single <AthleticsEventsRepository> {
    AthleticsEventsRepository(
      applicationContext = androidContext(),
      jsonFileName = allEventsJsonFileName
    )
  }

  single <EventGroupsRepository> {
    EventGroupsRepository(
      applicationContext = androidContext(),
      jsonFileName = allEventGroupsJsonFileName
    )
  }

  single <RankingScorePerformanceRepository>{
    RankingScorePerformanceRepository(
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
}

val roomTestModule = module {
  single {
    // In-Memory database config
    Room.inMemoryDatabaseBuilder(androidContext(), RankingScoreDatabase::class.java)
      .allowMainThreadQueries()
      .build()
  }
}

