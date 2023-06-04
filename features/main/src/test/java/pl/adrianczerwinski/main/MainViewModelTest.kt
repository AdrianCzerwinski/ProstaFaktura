package pl.adrianczerwinski.main

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
        mainViewModel.handleUiEvent(MainUiEvent.AddButtonPressed)
        runCurrent()

        assertThat(mainViewModel.states.value.showBottomSheetPicker).isTrue()
    }

    @Test
    fun `AddClientPressed event triggers OpenNewClient action`() = runTest {
        mainViewModel.actions.test {
            mainViewModel.handleUiEvent(MainUiEvent.AddClientPressed)
            runCurrent()

            assertThat(expectItem()).isEqualTo(MainUiAction.OpenNewClient)
        }
    }

    @Test
    fun `CloseBottomSheet event updates showBottomSheetPicker to false`() = runTest {
        mainViewModel.handleUiEvent(MainUiEvent.AddButtonPressed)
        mainViewModel.handleUiEvent(MainUiEvent.CloseBottomSheet)
        runCurrent()

        assertThat(mainViewModel.states.value.showBottomSheetPicker).isFalse()
    }

    @Test
    fun `CreateInvoicePressed event triggers OpenNewInvoice action`() = runTest {
        mainViewModel.actions.test {
            mainViewModel.handleUiEvent(MainUiEvent.CreateInvoicePressed)
            runCurrent()

            assertThat(expectItem()).isEqualTo(MainUiAction.OpenNewInvoice)
        }
    }

    @Test
    fun `SettingsPressed event triggers OpenSettings action`() = runTest {
        mainViewModel.actions.test {
            mainViewModel.handleUiEvent(MainUiEvent.SettingsPressed)
            runCurrent()

            assertThat(expectItem()).isEqualTo(MainUiAction.OpenSettings)
        }
    }
}
