package com.tatoeapps.athleticsrankingpoints.presentation.screens.savedscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tatoeapps.athleticsrankingpoints.data.entities.RankingScorePerformanceData
import com.tatoeapps.athleticsrankingpoints.domain.interfaces.RankingScorePerformanceRepository
import kotlinx.coroutines.launch

class PerformancesViewModel(private val performanceRepository: RankingScorePerformanceRepository) : ViewModel() {

  private var listOfPerformances = MutableLiveData<List<RankingScorePerformanceData>>(listOf())
  fun getListOfPerformances() :LiveData<List<RankingScorePerformanceData>> = listOfPerformances

  private var searchText = MutableLiveData("")
  fun getSearchText() : LiveData<String> = searchText

  init {
    loadPerformances()
  }

  private fun loadPerformances() = viewModelScope.launch {
    listOfPerformances.postValue(performanceRepository.getAllRankingScorePerformances())
  }

  var shouldResetSearchResults = false
  fun updateSearchText(newValue: String) = viewModelScope.launch {
    searchText.value=newValue
    if (newValue.length>2) {
      listOfPerformances.postValue(performanceRepository.getSearchedRankingScorePerformances(newValue))
      shouldResetSearchResults = true
    }
    if (newValue.length<=2 && shouldResetSearchResults) {
      listOfPerformances.postValue(performanceRepository.getAllRankingScorePerformances())
    }
  }
}