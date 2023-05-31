package pl.adrianczerwinski.ui

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import pl.adrianczerwinski.ui.theme.ProstaFakturaTheme

@Composable
fun BoxPreview(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) = ProstaFakturaTheme(dynamicColor = false, darkTheme = isSystemInDarkTheme()) {
    Box(modifier) { content() }
}

@Composable
fun ColumnPreview(
    modifier: Modifier = Modifier,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    content: @Composable ColumnScope.() -> Unit
) = ProstaFakturaTheme(dynamicColor = false, darkTheme = isSystemInDarkTheme()) {
    Column(modifier, verticalArrangement, horizontalAlignment) { content() }
}

@Composable
fun RowPreview(
    modifier: Modifier = Modifier,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    verticalAlignment: Alignment.Vertical = Alignment.Top,
    content: @Composable RowScope.() -> Unit
) = ProstaFakturaTheme(dynamicColor = false, darkTheme = isSystemInDarkTheme()) {
    Row(modifier, horizontalArrangement, verticalAlignment) { content() }
}

@Composable
fun ScreenPreview(content: @Composable () -> Unit) = ProstaFakturaTheme(dynamicColor = false, darkTheme = isSystemInDarkTheme()) {
    Column(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        content()
    }
}

@Preview(
    name = "Light mode",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showBackground = true
)
@Preview(
    name = "Dark mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
annotation class LightDarkPreview

@Preview(
    name = "Light mode",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    widthDp = 400,
    heightDp = 720,
    showBackground = true
)
@Preview(
    name = "Dark mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    widthDp = 400,
    heightDp = 720,
    showBackground = true
)
@Preview(
    name = "Small screen",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    widthDp = 320,
    heightDp = 520,
    showBackground = true
)
annotation class ScreenLightDarkPreview
