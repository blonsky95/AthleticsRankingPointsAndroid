package com.example.athleticsrankingpoints.di

import com.example.athleticsrankingpoints.repository.AthleticsEventsRepository
import com.example.athleticsrankingpoints.repository.AthleticsEventsRepositoryImpl
import com.example.athleticsrankingpoints.repository.EventGroupsRepository
import com.example.athleticsrankingpoints.repository.EventGroupsRepositoryImpl
import com.example.athleticsrankingpoints.ui.eventgroupselector.EventGroupSelectorViewModel
import com.example.athleticsrankingpoints.ui.eventgroupselector.simulator.EventGroupSimulatorViewModel
import com.example.athleticsrankingpoints.ui.lookupscreen.LookUpViewModel
import org.koin.dsl.module
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel


const val allEventsJsonFileName = "all_events.json"
const val allEventGroupsJsonFileName = "event_groups.json"

val firstModule = module {

  //Because we are using an implementation of the interface we will specify the type
  single <AthleticsEventsRepository> {
    AthleticsEventsRepositoryImpl(
      applicationContext = androidContext(),
      jsonFileName = allEventsJsonFileName
    )
  }

  single <EventGroupsRepository> {
    EventGroupsRepositoryImpl(
      applicationContext = androidContext(),
      jsonFileName = allEventGroupsJsonFileName
    )
  }

  //requires the athleticsEventsRepositoryImpl above
  viewModel { LookUpViewModel(get()) }

  viewModel { EventGroupSelectorViewModel(get()) }

  viewModel { EventGroupSimulatorViewModel(get()) }


}