package pl.adrianczerwinski.onboarding.welcome

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import pl.adrianczerwinski.common.HandleAction
import pl.adrianczerwinski.onboarding.OnboardingFeatureNavigation
import pl.adrianczerwinski.onboarding.welcome.WelcomeUiAction.OpenMain
import pl.adrianczerwinski.onboarding.welcome.WelcomeUiAction.OpenSignIn
import pl.adrianczerwinski.onboarding.welcome.WelcomeUiEvent.ButtonPressed
import pl.adrianczerwinski.prostafaktura.features.onboarding.R
import pl.adrianczerwinski.ui.ScreenLightDarkPreview
import pl.adrianczerwinski.ui.ScreenPreview
import pl.adrianczerwinski.ui.components.ElevatedIconButton
import pl.adrianczerwinski.ui.components.PagerIndicator
import pl.adrianczerwinski.ui.components.SpacerLarge
import pl.adrianczerwinski.ui.components.SpacerXXLarge

@Composable
fun Welcome(
    navigation: OnboardingFeatureNavigation,
    viewModel: WelcomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.states.collectAsStateWithLifecycle()

    WelcomeScreen(
        uiEvent = viewModel::handleUiEvent,
        uiState = uiState
    )

    HandleAction(viewModel.actions) { action ->
        when (action) {
            OpenSignIn -> navigation.openSignIn()
            OpenMain -> navigation.openMainScreen()
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun WelcomeScreen(
    uiState: WelcomeUiState,
    uiEvent: (WelcomeUiEvent) -> Unit = {}
) = Column(
    modifier = Modifier
        .fillMaxSize()
        .padding(24.dp),
    horizontalAlignment = Alignment.CenterHorizontally
) {
    val pagerState = rememberPagerState()
    Column(modifier = Modifier.verticalScroll(rememberScrollState()), horizontalAlignment = Alignment.CenterHorizontally) {
        SpacerXXLarge()
        Text(
            text = stringResource(R.string.welcome),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
        SpacerLarge()
        Pager(pages = uiState.pages, pagerState = pagerState) { }
        SpacerLarge()
        PagerIndicator(pageCount = uiState.pages.size, currentPage = pagerState.currentPage)
        SpacerLarge()
    }
    Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.Bottom) {
        AnimatedVisibility(visible = !pagerState.canScrollForward, enter = fadeIn(), exit = ExitTransition.None) {
            ElevatedIconButton(
                text = stringResource(id = R.string.open_sign_in),
                icon = Icons.Default.KeyboardArrowRight
            ) {
                uiEvent(ButtonPressed)
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun Pager(
    pagerState: PagerState,
    pages: List<WelcomePageUiModel>,
    onPageChanged: (Int) -> Unit
) {
    HorizontalPager(
        modifier = Modifier.padding(24.dp),
        pageCount = pages.size,
        state = pagerState,
        verticalAlignment = Alignment.Top
    ) { currentPage ->
        onPageChanged(currentPage)
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(painter = painterResource(id = pages[currentPage].pageImage), contentDescription = "Onboarding image $currentPage")
            SpacerLarge()
            Text(
                text = stringResource(id = pages[currentPage].text),
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

@ScreenLightDarkPreview
@Composable
fun OnboardingScreenPreview() = ScreenPreview {
    WelcomeScreen(uiState = WelcomeUiState())
}
