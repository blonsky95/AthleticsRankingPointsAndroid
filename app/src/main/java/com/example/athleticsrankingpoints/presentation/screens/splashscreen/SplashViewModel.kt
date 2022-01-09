package com.example.athleticsrankingpoints.presentation.screens.splashscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.athleticsrankingpoints.domain.interfaces.AthleticsEventsRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SplashViewModel(private val athleticsEventsRepository: AthleticsEventsRepository): ViewModel() {
  private val _isLoading = MutableStateFlow(true)
  val isLoading = _isLoading.asStateFlow()

  init {
    viewModelScope.launch {
      athleticsEventsRepository
      _isLoading.value=false
    }
  }
}