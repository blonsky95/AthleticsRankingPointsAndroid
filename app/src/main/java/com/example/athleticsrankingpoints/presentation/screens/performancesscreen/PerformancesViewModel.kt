package com.example.athleticsrankingpoints.presentation.screens.performancesscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.athleticsrankingpoints.data.entity.RankingScorePerformanceData
import com.example.athleticsrankingpoints.domain.interfaces.RankingScorePerformanceRepository
import kotlinx.coroutines.launch

class PerformancesViewModel(performanceRepository: RankingScorePerformanceRepository) : ViewModel() {

  lateinit var listOfPerformances : LiveData<List<RankingScorePerformanceData>>
  //Here I do not have a mutable live data because i am grabbing it directly from repo,
  // any updates and changes should be unidirectional and should always come from the repo
  init {
    viewModelScope.launch {
      listOfPerformances = performanceRepository.getAllRankingScorePerformances()
    }
  }
}