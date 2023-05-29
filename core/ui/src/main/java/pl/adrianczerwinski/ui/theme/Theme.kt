package pl.adrianczerwinski.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = darkColorScheme(
    background = Background,
    primary = DarkBlue,
    secondary = LightDarkBlue,
    tertiary = Gray,
    surface = Teal,
    onSurface = DarkGray,
    tertiaryContainer = DarkTeal,
    onBackground = DarkBlue,
    onPrimary = SuperLightGray,
    onSecondary = Background,
    onTertiary = Background,
    onTertiaryContainer = DarkBlue
)

private val DarkColorScheme = lightColorScheme(
    background = DarkBlue,
    primary = Teal,
    secondary = Pink,
    tertiary = DarkBlue,
    surface = Pink,
    onSurface = Background,
    tertiaryContainer = DarkPink,
    onBackground = Background,
    onPrimary = Background,
    onSecondary = Background,
    onTertiary = Background,
    onTertiaryContainer = DarkBlue
)

@Composable
fun ProstaFakturaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            window.navigationBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
