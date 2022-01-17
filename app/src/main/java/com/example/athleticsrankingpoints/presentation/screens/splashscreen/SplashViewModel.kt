package com.example.athleticsrankingpoints.presentation.screens.splashscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.athleticsrankingpoints.domain.interfaces.AthleticsEventsRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SplashViewModel(private val athleticsEventsRepository: AthleticsEventsRepository): ViewModel() {

  fun getIsRepoLoading():LiveData<Boolean> = athleticsEventsRepository.getIsRepositoryLoading()
}