package com.example.athleticsrankingpoints

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.athleticsrankingpoints.ui.lookupscreen.LookUpViewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContent {
      WorldRankingApp()
    }
  }
}


//AthleticsRankingPointsTheme {
//  // A surface container using the 'background' color from the theme
//  Surface(color = MaterialTheme.colors.background) {
//    Greeting("Android")
//  }
//}

//@Composable
//fun Greeting(name: String) {
//  Text(text = "Hello $name!")
//}
//
//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//  AthleticsRankingPointsTheme {
//    Greeting("Android")
//  }
//}