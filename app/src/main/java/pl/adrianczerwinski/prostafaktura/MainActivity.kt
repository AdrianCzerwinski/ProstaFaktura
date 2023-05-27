package pl.adrianczerwinski.prostafaktura

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import pl.adrianczerwinski.prostafaktura.navigation.ProstaFakturaNavigation
import pl.adrianczerwinski.prostafaktura.ui.theme.ProstaFakturaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProstaFakturaTheme {
                val navController = rememberNavController()
                ProstaFakturaNavigation(navController = navController)
            }
        }
    }
}
