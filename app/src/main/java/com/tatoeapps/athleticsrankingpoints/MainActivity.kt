package com.tatoeapps.athleticsrankingpoints

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.tatoeapps.athleticsrankingpoints.presentation.screens.splashscreen.SplashViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


@ExperimentalAnimationApi
class MainActivity : ComponentActivity() {

  private val splashViewModel:SplashViewModel by viewModel()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    val splashScreen = installSplashScreen()

    splashScreen.apply {

      setKeepOnScreenCondition{
        splashViewModel.getIsAthleticsEventsRepoLoading().value!=true && splashViewModel.getIsEventGroupsRepoLoading().value!=true
      }
    }

    setContent {
      WorldRankingApp()
    }
  }
}
