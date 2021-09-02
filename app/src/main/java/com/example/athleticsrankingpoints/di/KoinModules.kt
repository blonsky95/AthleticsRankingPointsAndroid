package com.example.athleticsrankingpoints.di

import com.example.athleticsrankingpoints.repository.AthleticsEventsRepository
import com.example.athleticsrankingpoints.repository.AthleticsEventsRepositoryImpl
import com.example.athleticsrankingpoints.ui.lookupscreen.LookUpViewModel
import org.koin.dsl.module
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel


const val allEventsJsonFileName = "all_events.json"

val firstModule = module {

  //Because we are using an implementation of the interface we will specify the type
  single <AthleticsEventsRepository> {
    AthleticsEventsRepositoryImpl(
      applicationContext = androidContext(),
      jsonFileName = allEventsJsonFileName
    )
  }

  //requires the athleticsEventsRepositoryImpl above
  viewModel { LookUpViewModel(get()) }


}