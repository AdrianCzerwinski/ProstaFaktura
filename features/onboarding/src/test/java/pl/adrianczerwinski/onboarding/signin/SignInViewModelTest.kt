package pl.adrianczerwinski.onboarding.signin

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import pl.adrianczerwinski.common.resourceprovider.ResourceProvider
import pl.adrianczerwinski.domain.user.SaveUserUseCase
import pl.adrianczerwinski.domain.user.SetOnboardingShownUseCase
import pl.adrianczerwinski.domain.validation.ValidateEmailUseCase
import pl.adrianczerwinski.domain.validation.ValidatePhoneNumberUseCase
import pl.adrianczerwinski.domain.validation.ValidatePostalCodeUseCase
import pl.adrianczerwinski.domain.validation.ValidateSimpleInputUseCase
import pl.adrianczerwinski.onboarding.signin.SignInUiAction.OpenMain
import pl.adrianczerwinski.onboarding.signin.SignInUiEvent.AccountNumberChanged
import pl.adrianczerwinski.onboarding.signin.SignInUiEvent.CityChanged
import pl.adrianczerwinski.onboarding.signin.SignInUiEvent.CompanyNameChanged
import pl.adrianczerwinski.onboarding.signin.SignInUiEvent.ConfirmCompanyInfoPressed
import pl.adrianczerwinski.onboarding.signin.SignInUiEvent.ConfirmUserInfoPressed
import pl.adrianczerwinski.onboarding.signin.SignInUiEvent.EmailChanged
import pl.adrianczerwinski.onboarding.signin.SignInUiEvent.NameChanged
import pl.adrianczerwinski.onboarding.signin.SignInUiEvent.OthersKeyChanged
import pl.adrianczerwinski.onboarding.signin.SignInUiEvent.OthersValueChanged
import pl.adrianczerwinski.onboarding.signin.SignInUiEvent.PhoneNumberChanged
import pl.adrianczerwinski.onboarding.signin.SignInUiEvent.PostalCodeChanged
import pl.adrianczerwinski.onboarding.signin.SignInUiEvent.SaveOthersPressed
import pl.adrianczerwinski.onboarding.signin.SignInUiEvent.StreetAndNumberChanged
import pl.adrianczerwinski.onboarding.signin.SignInUiEvent.TaxNumberChanged
import pl.adrianczerwinski.onboarding.signin.SignInUiState.BottomSheet.Error
import pl.adrianczerwinski.onboarding.signin.SignInUiState.ScreenType.COMPANY
import pl.adrianczerwinski.user.model.Company
import pl.adrianczerwinski.user.model.User
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalCoroutinesApi::class, ExperimentalTime::class)
class SignInViewModelTest {
    private val saveUserUseCase = mockk<SaveUserUseCase>(relaxed = true)
    private val setOnboardingShownUseCase = mockk<SetOnboardingShownUseCase>(relaxed = true)
    private val validateEmailUseCase = mockk<ValidateEmailUseCase>()
    private val validatePhoneNumberUseCase = mockk<ValidatePhoneNumberUseCase>()
    private val validatePostalCodeUseCase = mockk<ValidatePostalCodeUseCase>()
    private val validateSimpleInputUseCase = mockk<ValidateSimpleInputUseCase>()
    private val resourceProvider = mockk<ResourceProvider>()

    private lateinit var signInViewModel: SignInViewModel
    private val dispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        MockKAnnotations.init(this)
        signInViewModel = SignInViewModel(
            saveUserUseCase, setOnboardingShownUseCase, validateEmailUseCase,
            validatePhoneNumberUseCase, validatePostalCodeUseCase, validateSimpleInputUseCase,
            resourceProvider
        )
        every { resourceProvider.getString(any()) } returns "Test error message"
        coEvery { validateSimpleInputUseCase(any()) } returns true
        coEvery { validateEmailUseCase(any()) } returns true
        coEvery { validatePhoneNumberUseCase(any()) } returns true
        coEvery { validatePostalCodeUseCase(any()) } returns true
    }

    @After
    fun cleanup() {
        Dispatchers.resetMain()
        unmockkAll()
    }

    @Test
    fun `onConfirmUserInfoPressed, when all inputs are valid, updates screenType to COMPANY`() = runTest {
        coEvery { validateSimpleInputUseCase(any()) } returns true
        coEvery { validateEmailUseCase(any()) } returns true
        coEvery { validatePhoneNumberUseCase(any()) } returns true

        signInViewModel.handleUiEvent(NameChanged("testName"))
        signInViewModel.handleUiEvent(EmailChanged("testEmail@example.com"))
        signInViewModel.handleUiEvent(PhoneNumberChanged("123456789"))
        signInViewModel.handleUiEvent(ConfirmUserInfoPressed)
        runCurrent()

        val newState = signInViewModel.states.value
        assertThat(newState.screenType).isEqualTo(COMPANY)
    }

    @Test
    fun `onConfirmUserInfoPressed, when name is invalid, updates name field with error`() = runTest {
        coEvery { validateSimpleInputUseCase(any()) } returns false
        coEvery { validateEmailUseCase(any()) } returns true
        coEvery { validatePhoneNumberUseCase(any()) } returns true

        signInViewModel.handleUiEvent(NameChanged("invalidName"))
        signInViewModel.handleUiEvent(EmailChanged("testEmail@example.com"))
        signInViewModel.handleUiEvent(PhoneNumberChanged("123456789"))
        signInViewModel.handleUiEvent(ConfirmUserInfoPressed)
        runCurrent()

        val newState = signInViewModel.states.value
        assertThat(newState.name.isError).isTrue()
        assertThat(newState.name.errorMessage).isNotNull()
    }

    @Test
    fun `onConfirmUserInfoPressed, when phone number is invalid, updates phone number field with error`() = runTest {
        coEvery { validateSimpleInputUseCase(any()) } returns true
        coEvery { validateEmailUseCase(any()) } returns true
        coEvery { validatePhoneNumberUseCase(any()) } returns false

        signInViewModel.handleUiEvent(NameChanged("testName"))
        signInViewModel.handleUiEvent(EmailChanged("testEmail@example.com"))
        signInViewModel.handleUiEvent(PhoneNumberChanged("invalidPhoneNumber"))
        signInViewModel.handleUiEvent(ConfirmUserInfoPressed)
        runCurrent()

        val newState = signInViewModel.states.value
        assertThat(newState.phoneNumber.isError).isTrue()
        assertThat(newState.phoneNumber.errorMessage).isNotNull()
    }

    @Test
    fun `onConfirmCompanyInfoPressed, when all company info is valid, triggers saveUser`() = runTest {
        coEvery { validateSimpleInputUseCase(any()) } returns true
        coEvery { validatePostalCodeUseCase(any()) } returns true
        coEvery { saveUserUseCase(any()) } returns Result.success(Unit)

        signInViewModel.handleUiEvent(CompanyNameChanged("validCompanyName"))
        signInViewModel.handleUiEvent(TaxNumberChanged("validTaxNumber"))
        signInViewModel.handleUiEvent(AccountNumberChanged("validAccountNumber"))
        signInViewModel.handleUiEvent(StreetAndNumberChanged("validStreetAndNumber"))
        signInViewModel.handleUiEvent(CityChanged("validCity"))
        signInViewModel.handleUiEvent(PostalCodeChanged("validPostalCode"))
        signInViewModel.handleUiEvent(ConfirmCompanyInfoPressed)
        runCurrent()

        coVerify { saveUserUseCase(any()) }
    }

    @Test
    fun `onConfirmCompanyInfoPressed, when any company info is invalid, updates state with error`() = runTest {
        coEvery { validateSimpleInputUseCase(any()) } returns true
        coEvery { validatePostalCodeUseCase(any()) } returns false

        signInViewModel.handleUiEvent(CompanyNameChanged("validCompanyName"))
        signInViewModel.handleUiEvent(TaxNumberChanged("validTaxNumber"))
        signInViewModel.handleUiEvent(AccountNumberChanged("validAccountNumber"))
        signInViewModel.handleUiEvent(StreetAndNumberChanged("validStreetAndNumber"))
        signInViewModel.handleUiEvent(CityChanged("validCity"))
        signInViewModel.handleUiEvent(PostalCodeChanged("invalidPostalCode"))
        signInViewModel.handleUiEvent(ConfirmCompanyInfoPressed)
        runCurrent()

        val newState = signInViewModel.states.value
        assertThat(newState.isError).isTrue()
    }

    @Test
    fun `SaveOthersPressed, when key and value are valid, updates others map and resets key and value states`() = runTest {
        coEvery { validateSimpleInputUseCase(any()) } returns true

        signInViewModel.handleUiEvent(OthersKeyChanged("validKey"))
        signInViewModel.handleUiEvent(OthersValueChanged("validValue"))
        signInViewModel.handleUiEvent(SaveOthersPressed)
        runCurrent()

        val newState = signInViewModel.states.value
        assertThat(newState.newOthersKey.isError).isFalse()
        assertThat(newState.newOthersKey.errorMessage).isNull()
        assertThat(newState.newOthersValue.isError).isFalse()
        assertThat(newState.newOthersValue.errorMessage).isNull()
    }

    @Test
    fun `SaveOthersPressed, when either key or value are invalid, updates key and value states with error`() = runTest {
        coEvery { validateSimpleInputUseCase("validKey") } returns false
        coEvery { validateSimpleInputUseCase("invalidValue") } returns false

        signInViewModel.handleUiEvent(OthersKeyChanged("validKey"))
        signInViewModel.handleUiEvent(OthersValueChanged("invalidValue"))
        signInViewModel.handleUiEvent(SaveOthersPressed)
        runCurrent()

        val newState = signInViewModel.states.value
        assertThat(newState.newOthersKey.isError).isTrue()
        assertThat(newState.newOthersKey.errorMessage).isNotNull()
        assertThat(newState.newOthersValue.isError).isTrue()
        assertThat(newState.newOthersValue.errorMessage).isNotNull()
    }

    @Test
    fun `saveUser, when saving is successful, triggers OpenMain action and calls setOnboardingShownUseCase`() = runTest {
        val user = User(
            name = "Test Name",
            phoneNumber = "123456789",
            email = "testEmail@example.com",
            company = Company(
                name = "Test Company",
                taxNumber = "123456789",
                accountNumber = "987654321",
                streetAndNumber = "Test Street 123",
                city = "Test City",
                postalCode = "12345",
                others = mapOf()
            )
        )
        coEvery { saveUserUseCase(user) } returns Result.success(Unit)
        coJustRun { setOnboardingShownUseCase() }

        signInViewModel.actions.test {

            signInViewModel.handleUiEvent(NameChanged(user.name))
            signInViewModel.handleUiEvent(PhoneNumberChanged(user.phoneNumber))
            signInViewModel.handleUiEvent(EmailChanged(user.email))
            signInViewModel.handleUiEvent(CompanyNameChanged(user.company.name))
            signInViewModel.handleUiEvent(TaxNumberChanged(user.company.taxNumber))
            signInViewModel.handleUiEvent(AccountNumberChanged(user.company.accountNumber))
            signInViewModel.handleUiEvent(StreetAndNumberChanged(user.company.streetAndNumber))
            signInViewModel.handleUiEvent(CityChanged(user.company.city))
            signInViewModel.handleUiEvent(PostalCodeChanged(user.company.postalCode))
            signInViewModel.handleUiEvent(ConfirmCompanyInfoPressed)
            runCurrent()

            coVerify { setOnboardingShownUseCase() }
            assertThat(expectItem()).isEqualTo(OpenMain)
        }
    }

    @Test
    fun `saveUser, when saving fails, updates state with Error bottom sheet`() = runTest {
        val user = User(
            name = "Test Name",
            phoneNumber = "123456789",
            email = "testEmail@example.com",
            company = Company(
                name = "Test Company",
                taxNumber = "123456789",
                accountNumber = "987654321",
                streetAndNumber = "Test Street 123",
                city = "Test City",
                postalCode = "12345",
                others = mapOf()
            )
        )
        coEvery { saveUserUseCase(user) } returns Result.failure(Exception("Test exception"))

        signInViewModel.handleUiEvent(NameChanged(user.name))
        signInViewModel.handleUiEvent(PhoneNumberChanged(user.phoneNumber))
        signInViewModel.handleUiEvent(EmailChanged(user.email))
        signInViewModel.handleUiEvent(CompanyNameChanged(user.company.name))
        signInViewModel.handleUiEvent(TaxNumberChanged(user.company.taxNumber))
        signInViewModel.handleUiEvent(AccountNumberChanged(user.company.accountNumber))
        signInViewModel.handleUiEvent(StreetAndNumberChanged(user.company.streetAndNumber))
        signInViewModel.handleUiEvent(CityChanged(user.company.city))
        signInViewModel.handleUiEvent(PostalCodeChanged(user.company.postalCode))
        signInViewModel.handleUiEvent(ConfirmCompanyInfoPressed)
        runCurrent()

        val newState = signInViewModel.states.value
        assertThat(newState.bottomSheet).isEqualTo(Error)
    }
}
