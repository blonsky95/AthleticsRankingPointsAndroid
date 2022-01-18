package com.example.athleticsrankingpoints.presentation.screens.splashscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.athleticsrankingpoints.domain.interfaces.AthleticsEventsRepository
import com.example.athleticsrankingpoints.domain.interfaces.EventGroupsRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SplashViewModel(
  private val athleticsEventsRepository: AthleticsEventsRepository,
  private val eventGroupsRepository: EventGroupsRepository
  ): ViewModel() {

  fun getIsAthleticsEventsRepoLoading():LiveData<Boolean> = athleticsEventsRepository.getIsRepositoryLoading()
  fun getIsEventGroupsRepoLoading():LiveData<Boolean> = eventGroupsRepository.getIsRepositoryLoading()

}