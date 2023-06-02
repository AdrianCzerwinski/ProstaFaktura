package pl.adrianczerwinski.prostafaktura

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import pl.adrianczerwinski.prostafaktura.navigation.ProstaFakturaNavigation
import pl.adrianczerwinski.ui.theme.ProstaFakturaTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProstaFakturaTheme(dynamicColor = false) {
                val navController = rememberNavController()
                ProstaFakturaNavigation(navController = navController)
            }
        }
    }
}
