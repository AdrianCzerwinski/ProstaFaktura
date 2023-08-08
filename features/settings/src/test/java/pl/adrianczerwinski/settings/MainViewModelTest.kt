package pl.adrianczerwinski.settings

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test
import pl.adrianczerwinski.main.MainUiAction.OpenNewClient
import pl.adrianczerwinski.main.MainUiAction.OpenNewInvoice
import pl.adrianczerwinski.main.MainUiAction.OpenSettings
import pl.adrianczerwinski.main.MainUiEvent.AddButtonPressed
import pl.adrianczerwinski.main.MainUiEvent.AddClientPressed
import pl.adrianczerwinski.main.MainUiEvent.CloseBottomSheet
import pl.adrianczerwinski.main.MainUiEvent.CreateInvoicePressed
import pl.adrianczerwinski.main.MainUiEvent.SettingsPressed
import pl.adrianczerwinski.main.MainViewModel
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalCoroutinesApi::class, ExperimentalTime::class)
class MainViewModelTest {
    private lateinit var mainViewModel: MainViewModel
    private val dispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        mainViewModel = MainViewModel()
    }

    @Test
    fun `AddButtonPressed event updates showBottomSheetPicker to true`() = runTest {
        mainViewModel.handleUiEvent(AddButtonPressed)
        runCurrent()

        assertThat(mainViewModel.states.value.showBottomSheetPicker).isTrue()
    }

    @Test
    fun `AddClientPressed event triggers OpenNewClient action`() = runTest {
        mainViewModel.actions.test {
            mainViewModel.handleUiEvent(AddClientPressed)
            runCurrent()

            assertThat(expectItem()).isEqualTo(OpenNewClient)
        }
    }

    @Test
    fun `CloseBottomSheet event updates showBottomSheetPicker to false`() = runTest {
        mainViewModel.handleUiEvent(AddButtonPressed)
        mainViewModel.handleUiEvent(CloseBottomSheet)
        runCurrent()

        assertThat(mainViewModel.states.value.showBottomSheetPicker).isFalse()
    }

    @Test
    fun `CreateInvoicePressed event triggers OpenNewInvoice action`() = runTest {
        mainViewModel.actions.test {
            mainViewModel.handleUiEvent(CreateInvoicePressed)
            runCurrent()

            assertThat(expectItem()).isEqualTo(OpenNewInvoice)
        }
    }

    @Test
    fun `SettingsPressed event triggers OpenSettings action`() = runTest {
        mainViewModel.actions.test {
            mainViewModel.handleUiEvent(SettingsPressed)
            runCurrent()

            assertThat(expectItem()).isEqualTo(OpenSettings)
        }
    }
}
