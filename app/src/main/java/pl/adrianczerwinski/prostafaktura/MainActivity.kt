package pl.adrianczerwinski.prostafaktura

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
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
                val view = LocalView.current
                val statusBarColor = MaterialTheme.colorScheme.background.toArgb()
                val navigationBarColor = MaterialTheme.colorScheme.onSecondary.toArgb()
                val darkTheme = isSystemInDarkTheme()

                SideEffect {
                    val window = (view.context as Activity).window
                    window.statusBarColor = statusBarColor
                    window.navigationBarColor = navigationBarColor
                    WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
                }

                ProstaFakturaNavigation(navController = navController)
            }
        }
    }
}
