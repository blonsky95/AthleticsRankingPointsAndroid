package com.example.athleticsrankingpoints

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.athleticsrankingpoints.presentation.screens.splashscreen.SplashViewModel
import org.koin.androidx.compose.getViewModel
import org.koin.androidx.compose.viewModel

class MainActivity : ComponentActivity() {

  private val splashViewModel:SplashViewModel by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    val splashScreen = installSplashScreen()

    splashScreen.apply {
      setKeepVisibleCondition{
        splashViewModel.isLoading.value
      }
    }

    setContent {
      WorldRankingApp()
    }
  }
}
