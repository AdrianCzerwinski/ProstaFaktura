package pl.adrianczerwinski.onboarding

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test
import pl.adrianczerwinski.domain.user.GetUserUseCase
import pl.adrianczerwinski.onboarding.welcome.WelcomeUiAction.OpenSignIn
import pl.adrianczerwinski.onboarding.welcome.WelcomeUiEvent.ButtonPressed
import pl.adrianczerwinski.onboarding.welcome.WelcomeViewModel
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalCoroutinesApi::class, ExperimentalTime::class)
class WelcomeViewModelTest {
    private lateinit var viewModel: WelcomeViewModel
    private val testDispatcher = StandardTestDispatcher()
    private val getUserUseCase: GetUserUseCase = mockk()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = WelcomeViewModel(getUserUseCase)
        coEvery { getUserUseCase.invoke() } returns flowOf(Result.success(null))
    }

    @Test
    fun `Test ButtonPressed, should navigate to SignIn`() = runTest {
        viewModel.actions.test {
            viewModel.handleUiEvent(ButtonPressed)

            assertThat(expectItem()).isEqualTo(OpenSignIn)
        }
    }
}
