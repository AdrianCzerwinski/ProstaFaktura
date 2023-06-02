package pl.adrianczerwinski.onboarding.signin

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import pl.adrianczerwinski.common.StateActionsViewModel
import pl.adrianczerwinski.common.action
import pl.adrianczerwinski.common.resourceprovider.ResourceProvider
import pl.adrianczerwinski.domain.user.SaveUserUseCase
import pl.adrianczerwinski.domain.user.SetOnboardingShownUseCase
import pl.adrianczerwinski.domain.validation.ValidateEmailUseCase
import pl.adrianczerwinski.domain.validation.ValidatePhoneNumberUseCase
import pl.adrianczerwinski.domain.validation.ValidatePostalCodeUseCase
import pl.adrianczerwinski.domain.validation.ValidateSimpleInputUseCase
import pl.adrianczerwinski.onboarding.signin.SignInUiAction.OpenMain
import pl.adrianczerwinski.onboarding.signin.SignInUiEvent.AccountNumberChanged
import pl.adrianczerwinski.onboarding.signin.SignInUiEvent.AddOthersPressed
import pl.adrianczerwinski.onboarding.signin.SignInUiEvent.CityChanged
import pl.adrianczerwinski.onboarding.signin.SignInUiEvent.CloseBottomSheet
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
import pl.adrianczerwinski.onboarding.signin.SignInUiEvent.TryAgainPressed
import pl.adrianczerwinski.onboarding.signin.SignInUiState.BottomSheet.Error
import pl.adrianczerwinski.onboarding.signin.SignInUiState.BottomSheet.Others
import pl.adrianczerwinski.onboarding.signin.SignInUiState.ScreenType.COMPANY
import pl.adrianczerwinski.onboarding.signin.SignInUiState.ScreenType.USER
import pl.adrianczerwinski.prostafaktura.core.ui.R
import pl.adrianczerwinski.ui.models.TextFieldState
import pl.adrianczerwinski.user.model.Company
import pl.adrianczerwinski.user.model.User
import javax.inject.Inject

data class SignInUiState(
    val name: TextFieldState = TextFieldState(),
    val email: TextFieldState = TextFieldState(),
    val phoneNumber: TextFieldState = TextFieldState(),
    val companyName: TextFieldState = TextFieldState(),
    val taxNumber: TextFieldState = TextFieldState(),
    val accountNumber: TextFieldState = TextFieldState(),
    val streetAndNumber: TextFieldState = TextFieldState(),
    val city: TextFieldState = TextFieldState(),
    val postalCode: TextFieldState = TextFieldState(),
    val newOthersKey: TextFieldState = TextFieldState(),
    val newOthersValue: TextFieldState = TextFieldState(),
    val others: Map<String, String> = mapOf(),
    val screenType: ScreenType = USER,
    val isError: Boolean = false,
    val bottomSheet: BottomSheet? = null
) {
    enum class ScreenType { USER, COMPANY }

    sealed class BottomSheet {
        object Others : BottomSheet()
        object Error : BottomSheet()
    }
}

sealed class SignInUiAction {
    object OpenMain : SignInUiAction()
}

sealed class SignInUiEvent {
    data class NameChanged(val name: String) : SignInUiEvent()
    data class EmailChanged(val email: String) : SignInUiEvent()
    data class PhoneNumberChanged(val phoneNumber: String) : SignInUiEvent()
    data class CompanyNameChanged(val companyName: String) : SignInUiEvent()
    data class TaxNumberChanged(val taxNumber: String) : SignInUiEvent()
    data class StreetAndNumberChanged(val streetAndNumber: String) : SignInUiEvent()
    data class CityChanged(val city: String) : SignInUiEvent()
    data class PostalCodeChanged(val postalCode: String) : SignInUiEvent()
    data class OthersValueChanged(val value: String) : SignInUiEvent()
    data class OthersKeyChanged(val key: String) : SignInUiEvent()
    data class AccountNumberChanged(val accountNumber: String) : SignInUiEvent()
    object ConfirmUserInfoPressed : SignInUiEvent()
    object ConfirmCompanyInfoPressed : SignInUiEvent()
    object AddOthersPressed : SignInUiEvent()
    object CloseBottomSheet : SignInUiEvent()
    object TryAgainPressed : SignInUiEvent()
    object SaveOthersPressed : SignInUiEvent()
}

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val saveUserUseCase: SaveUserUseCase,
    private val setOnboardingShownUseCase: SetOnboardingShownUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePhoneNumberUseCase: ValidatePhoneNumberUseCase,
    private val validatePostalCodeUseCase: ValidatePostalCodeUseCase,
    private val validateSimpleInputUseCase: ValidateSimpleInputUseCase,
    private val resourceProvider: ResourceProvider
) : StateActionsViewModel<SignInUiState, SignInUiAction>(SignInUiState()) {

    @Suppress("CyclomaticComplexMethod")
    fun handleUiEvent(event: SignInUiEvent) {
        when (event) {
            is ConfirmUserInfoPressed -> onConfirmUserInfoPressed()
            is EmailChanged -> updateState { copy(email = TextFieldState(value = event.email)) }
            is NameChanged -> updateState { copy(name = TextFieldState(value = event.name)) }
            is PhoneNumberChanged -> updateState { copy(phoneNumber = TextFieldState(value = event.phoneNumber)) }
            is ConfirmCompanyInfoPressed -> onConfirmCompanyInfoPressed()
            is AccountNumberChanged -> updateState { copy(accountNumber = TextFieldState(value = event.accountNumber)) }
            is CityChanged -> updateState { copy(city = TextFieldState(value = event.city)) }
            is CompanyNameChanged -> updateState { copy(companyName = TextFieldState(value = event.companyName)) }
            is OthersValueChanged -> updateState { copy(newOthersValue = TextFieldState(value = event.value)) }
            is OthersKeyChanged -> updateState { copy(newOthersKey = TextFieldState(value = event.key)) }
            is PostalCodeChanged -> updateState { copy(postalCode = TextFieldState(value = event.postalCode)) }
            is StreetAndNumberChanged -> updateState { copy(streetAndNumber = TextFieldState(value = event.streetAndNumber)) }
            is TaxNumberChanged -> updateState { copy(taxNumber = TextFieldState(value = event.taxNumber)) }
            is AddOthersPressed -> updateState { copy(bottomSheet = Others) }
            is CloseBottomSheet -> updateState { copy(bottomSheet = null) }
            is TryAgainPressed -> saveUser()
            is SaveOthersPressed -> onOthersChanged()
        }
    }

    private fun onConfirmUserInfoPressed() = viewModelScope.launch {
        val nameValidationResult = validateSimpleInputUseCase(states.value.name.value)
        val emailValidationResult = validateEmailUseCase(states.value.email.value)
        val phoneNumberValidationResult = validatePhoneNumberUseCase(states.value.phoneNumber.value)
        val isSuccess = listOf(nameValidationResult, emailValidationResult, phoneNumberValidationResult).all { it }

        if (isSuccess) {
            updateState { copy(screenType = COMPANY) }
        } else {
            updateState {
                copy(
                    name = name.copy(
                        isError = !nameValidationResult,
                        errorMessage = getErrorMessage(isError = !nameValidationResult)
                    ),
                    phoneNumber = phoneNumber.copy(
                        isError = !phoneNumberValidationResult,
                        errorMessage = getErrorMessage(isError = !phoneNumberValidationResult)
                    ),
                    email = email.copy(
                        isError = !emailValidationResult,
                        errorMessage = getErrorMessage(isError = !emailValidationResult)
                    ),
                    isError = true
                )
            }
        }
    }

    private fun onConfirmCompanyInfoPressed() = viewModelScope.launch {
        val validationResult = validateCompanyInputs()
        if (!validationResult) updateState { copy(isError = true) } else saveUser()
    }

    private fun saveUser() = viewModelScope.launch {
        val result = saveUserUseCase(
            User(
                name = states.value.name.value,
                phoneNumber = states.value.phoneNumber.value,
                email = states.value.email.value,
                company = Company(
                    name = states.value.companyName.value,
                    taxNumber = states.value.taxNumber.value,
                    accountNumber = states.value.accountNumber.value,
                    streetAndNumber = states.value.streetAndNumber.value,
                    city = states.value.city.value,
                    postalCode = states.value.postalCode.value,
                    others = states.value.others
                )
            )
        )
        if (result.isSuccess) {
            setOnboardingShownUseCase()
            action(OpenMain)
        } else {
            updateState { copy(bottomSheet = Error) }
        }
    }

    private fun validateCompanyInputs(): Boolean {
        val companyNameValidationResult = validateSimpleInputUseCase(states.value.companyName.value)
        val taxNumberValidationResult = validateSimpleInputUseCase(states.value.taxNumber.value)
        val accountNumberValidationResult = validateSimpleInputUseCase(states.value.accountNumber.value)
        val streetValidationResult = validateSimpleInputUseCase(states.value.streetAndNumber.value)
        val cityValidationResult = validateSimpleInputUseCase(states.value.city.value)
        val postalCodeValidationResult = validatePostalCodeUseCase(states.value.postalCode.value)

        updateState {
            copy(
                companyName = companyName.copy(
                    isError = !companyNameValidationResult,
                    errorMessage = getErrorMessage(isError = !companyNameValidationResult)
                ),
                taxNumber = taxNumber.copy(
                    isError = !taxNumberValidationResult,
                    errorMessage = getErrorMessage(isError = !taxNumberValidationResult)
                ),
                accountNumber = accountNumber.copy(
                    isError = !accountNumberValidationResult,
                    errorMessage = getErrorMessage(isError = !accountNumberValidationResult)
                ),
                streetAndNumber = streetAndNumber.copy(
                    isError = !streetValidationResult,
                    errorMessage = getErrorMessage(isError = !streetValidationResult)
                ),
                city = city.copy(
                    isError = !cityValidationResult,
                    errorMessage = getErrorMessage(isError = !cityValidationResult)
                ),
                postalCode = postalCode.copy(
                    isError = !postalCodeValidationResult,
                    errorMessage = getErrorMessage(isError = !postalCodeValidationResult)
                )
            )
        }

        return listOf(
            companyNameValidationResult,
            taxNumberValidationResult,
            accountNumberValidationResult,
            streetValidationResult,
            cityValidationResult,
            postalCodeValidationResult
        ).all { it }
    }

    private fun onOthersChanged() {
        val newKeyValidation = validateSimpleInputUseCase(states.value.newOthersKey.value)
        val newValueValidation = validateSimpleInputUseCase(states.value.newOthersKey.value)
        val newCompanyData = mapOf(states.value.newOthersKey.value to states.value.newOthersValue.value)

        val validationResult = listOf(newKeyValidation, newValueValidation).all { it }

        if (validationResult) {
            updateState {
                copy(
                    others = others.plus(newCompanyData),
                    bottomSheet = null,
                    newOthersKey = TextFieldState(),
                    newOthersValue = TextFieldState()
                )
            }
        } else {
            updateState {
                copy(
                    newOthersKey = newOthersKey.copy(
                        isError = true,
                        errorMessage = resourceProvider.getString(R.string.error_value_message)
                    ),
                    newOthersValue = newOthersValue.copy(
                        isError = true,
                        errorMessage = resourceProvider.getString(R.string.error_value_message)
                    ),
                )
            }
        }
    }

    private fun getErrorMessage(isError: Boolean) = if (isError) resourceProvider.getString(R.string.error_value_message) else null
}
