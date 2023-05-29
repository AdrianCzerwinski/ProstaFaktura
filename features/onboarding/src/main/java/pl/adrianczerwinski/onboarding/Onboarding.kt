package pl.adrianczerwinski.onboarding

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import pl.adrianczerwinski.common.HandleAction
import pl.adrianczerwinski.onboarding.OnboardingUiAction.OpenSignIn
import pl.adrianczerwinski.onboarding.OnboardingUiEvent.ButtonPressed
import pl.adrianczerwinski.prostafaktura.features.onboarding.R
import pl.adrianczerwinski.ui.components.ElevatedIconButton
import pl.adrianczerwinski.ui.components.PagerIndicator
import pl.adrianczerwinski.ui.components.SpacerLarge
import pl.adrianczerwinski.ui.components.SpacerXXLarge

@Composable
fun Onboarding(
    navigation: OnboardingFeatureNavigation,
    viewModel: OnboardingViewModel = hiltViewModel()
) {
    val uiState by viewModel.states.collectAsStateWithLifecycle()

    OnboardingScreen(
        uiEvent = viewModel::handleUiEvent,
        uiState = uiState
    )

    HandleAction(viewModel.actions) { action ->
        when (action) {
            OpenSignIn -> navigation.openUserInfo()
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun OnboardingScreen(
    uiState: OnboardingUiState,
    uiEvent: (OnboardingUiEvent) -> Unit = {}
) = Column(
    modifier = Modifier
        .fillMaxSize()
        .padding(24.dp),
    horizontalAlignment = Alignment.CenterHorizontally
) {
    val pagerState = rememberPagerState()
    SpacerXXLarge()
    Text(text = stringResource(R.string.welcome), style = MaterialTheme.typography.titleLarge)
    SpacerLarge()
    Pager(pages = uiState.pages, pagerState = pagerState) { }
    SpacerLarge()
    PagerIndicator(pageCount = uiState.pages.size, currentPage = pagerState.currentPage)
    SpacerLarge()
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
    pages: List<PageUiModel>,
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
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview
@Composable
fun OnboardingScreenPreview() = Column(
    modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)
) {
    OnboardingScreen(uiState = OnboardingUiState())
}
