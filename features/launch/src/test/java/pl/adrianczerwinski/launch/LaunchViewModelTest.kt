package pl.adrianczerwinski.launch

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test
import pl.adrianczerwinski.domain.user.GetOnboardingShownUseCase
import pl.adrianczerwinski.launch.LaunchUiAction.OpenMainScreen
import pl.adrianczerwinski.launch.LaunchUiAction.OpenOnboarding
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
@ExperimentalCoroutinesApi
@FlowPreview
class LaunchViewModelTest {
    private val getOnboardingShownUseCase = mockk<GetOnboardingShownUseCase>()
    private lateinit var launchViewModel: LaunchViewModel
    private val dispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        MockKAnnotations.init(this)
        launchViewModel = LaunchViewModel(getOnboardingShownUseCase)
    }

    @Test
    fun `when getUserRegisteredUseCase returns true, emits OpenMainScreen`() = runTest {
        coEvery { getOnboardingShownUseCase() } returns flowOf(Result.success(true))

        launchViewModel.actions.test {
            advanceUntilIdle()

            assertThat(expectItem()).isEqualTo(OpenMainScreen)
        }
    }

    @Test
    fun `when getUserRegisteredUseCase returns false, emits OpenOnboarding`() = runTest {
        coEvery { getOnboardingShownUseCase() } returns flowOf(Result.success(false))

        launchViewModel.actions.test {
            advanceUntilIdle()

            assertThat(expectItem()).isEqualTo(OpenOnboarding)
        }
    }

    @Test
    fun `when getUserRegisteredUseCase returns error, emits OpenOnboarding`() = runTest {
        coEvery { getOnboardingShownUseCase() } returns flowOf(Result.failure(Throwable()))

        launchViewModel.actions.test {
            advanceUntilIdle()

            assertThat(expectItem()).isEqualTo(OpenOnboarding)
        }
    }
}
