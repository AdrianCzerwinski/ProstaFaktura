package pl.adrianczerwinski.prostafaktura

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.SideEffect
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import pl.adrianczerwinski.prostafaktura.navigation.ProstaFakturaNavigation
import pl.adrianczerwinski.ui.theme.ProstaFakturaTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val systemUiController = rememberSystemUiController()
            val useDarkIcons = !isSystemInDarkTheme()

            ProstaFakturaTheme(dynamicColor = false) {
                val backgroundColor = MaterialTheme.colorScheme.background
                SideEffect {
                    systemUiController.setSystemBarsColor(
                        color = backgroundColor,
                        darkIcons = useDarkIcons
                    )
                }
                val navController = rememberNavController()
                ProstaFakturaNavigation(navController = navController)
            }
        }
    }
}
