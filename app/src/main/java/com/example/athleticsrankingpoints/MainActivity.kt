package com.example.athleticsrankingpoints

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.athleticsrankingpoints.presentation.screens.splashscreen.SplashViewModel
import org.koin.android.compat.ScopeCompat.getViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : ComponentActivity() {

  private val splashViewModel:SplashViewModel by viewModel()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    val splashScreen = installSplashScreen()

    splashScreen.apply {
      setKeepVisibleCondition{
        splashViewModel.getIsRepoLoading().value!=true
//        splashViewModel.isLoading.value
      }
    }

    setContent {
      WorldRankingApp()
    }
  }
}
