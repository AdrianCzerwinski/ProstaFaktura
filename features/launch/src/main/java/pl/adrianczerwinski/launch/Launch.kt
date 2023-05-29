package pl.adrianczerwinski.launch

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import pl.adrianczerwinski.common.HandleAction
import pl.adrianczerwinski.launch.LaunchUiAction.OpenMainScreen
import pl.adrianczerwinski.launch.LaunchUiAction.OpenOnboarding
import pl.adrianczerwinski.prostafaktura.core.ui.R as uiR

@Composable
fun Launch(
    navigation: LaunchFeatureNavigation,
    viewModel: LaunchViewModel = hiltViewModel()
) {
    LaunchScreen()

    HandleAction(action = viewModel.actions) { action ->
        when (action) {
            OpenMainScreen -> navigation.openMainScreen()
            OpenOnboarding -> navigation.openOnboarding()
        }
    }
}

@Composable
private fun LaunchScreen() = Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
    val infiniteTransition = rememberInfiniteTransition(label = "Launch infinite transition")
    val iconAlpha by infiniteTransition.animateFloat(
        initialValue = 0.2f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 800, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "Icon alpha transition"
    )
    Icon(
        modifier = Modifier
            .size(appIconSize)
            .alpha(iconAlpha),
        painter = painterResource(id = uiR.drawable.ic_prosta_faktura_icon),
        contentDescription = "App Icon",
        tint = Color.Unspecified
    )
}

private val appIconSize = 200.dp

@Preview
@Composable
fun LaunchScreenPreview() = LaunchScreen()
