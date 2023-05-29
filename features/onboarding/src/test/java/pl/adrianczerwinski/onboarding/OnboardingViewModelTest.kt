package pl.adrianczerwinski.onboarding

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test
import pl.adrianczerwinski.onboarding.OnboardingUiAction.OpenSignIn
import pl.adrianczerwinski.onboarding.OnboardingUiEvent.ButtonPressed
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalCoroutinesApi::class, ExperimentalTime::class)
class OnboardingViewModelTest {
    private lateinit var viewModel: OnboardingViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = OnboardingViewModel()
    }

    @Test
    fun `Test ButtonPressed, should navigate to SignIn`() = runTest {
        viewModel.actions.test {
            viewModel.handleUiEvent(ButtonPressed)

            assertThat(expectItem()).isEqualTo(OpenSignIn)
        }
    }
}
