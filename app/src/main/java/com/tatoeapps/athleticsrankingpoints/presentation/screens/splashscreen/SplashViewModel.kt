package com.tatoeapps.athleticsrankingpoints.presentation.screens.splashscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.tatoeapps.athleticsrankingpoints.domain.interfaces.AthleticsEventsRepository
import com.tatoeapps.athleticsrankingpoints.domain.interfaces.EventGroupsRepository

class SplashViewModel(
  private val athleticsEventsRepository: AthleticsEventsRepository,
  private val eventGroupsRepository: EventGroupsRepository
  ): ViewModel() {

  fun getIsAthleticsEventsRepoLoading():LiveData<Boolean> = athleticsEventsRepository.getIsRepositoryLoading()
  fun getIsEventGroupsRepoLoading():LiveData<Boolean> = eventGroupsRepository.getIsRepositoryLoading()

}