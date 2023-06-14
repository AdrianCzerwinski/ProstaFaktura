package pl.adrianczerwinski.addclient

import dagger.hilt.android.lifecycle.HiltViewModel
import pl.adrianczerwinski.addclient.AddClientUiAction.CloseAddClientFlow
import pl.adrianczerwinski.addclient.AddClientUiEvent.BackPressed
import pl.adrianczerwinski.addclient.AddClientUiEvent.ChooseCurrencyPressed
import pl.adrianczerwinski.addclient.AddClientUiEvent.ChooseLanguagePressed
import pl.adrianczerwinski.addclient.AddClientUiEvent.CityChanged
import pl.adrianczerwinski.addclient.AddClientUiEvent.NameChanged
import pl.adrianczerwinski.addclient.AddClientUiEvent.CompanyTypePressed
import pl.adrianczerwinski.addclient.AddClientUiEvent.CountrySelected
import pl.adrianczerwinski.addclient.AddClientUiEvent.EmailChanged
import pl.adrianczerwinski.addclient.AddClientUiEvent.OpenClientSettingsPressed
import pl.adrianczerwinski.addclient.AddClientUiEvent.OthersKeyChanged
import pl.adrianczerwinski.addclient.AddClientUiEvent.OthersValueChanged
import pl.adrianczerwinski.addclient.AddClientUiEvent.PersonTypePressed
import pl.adrianczerwinski.addclient.AddClientUiEvent.PostalCodeChanged
import pl.adrianczerwinski.addclient.AddClientUiEvent.SavePressed
import pl.adrianczerwinski.addclient.AddClientUiEvent.StreetAndNumberChanged
import pl.adrianczerwinski.addclient.AddClientUiEvent.TaxNumberChanged
import pl.adrianczerwinski.addclient.AddClientUiState.ClientCountry.GERMANY
import pl.adrianczerwinski.addclient.AddClientUiState.ClientCountry.POLAND
import pl.adrianczerwinski.addclient.AddClientUiState.ClientType.COMPANY
import pl.adrianczerwinski.addclient.AddClientUiState.ClientType.PERSON
import pl.adrianczerwinski.addclient.AddClientUiState.ScreenType.CLIENT_DATA
import pl.adrianczerwinski.addclient.AddClientUiState.ScreenType.CLIENT_SETTINGS
import pl.adrianczerwinski.common.StateActionsViewModel
import pl.adrianczerwinski.common.action
import pl.adrianczerwinski.common.resourceprovider.ResourceProvider
import pl.adrianczerwinski.domain.validation.ValidateEmailUseCase
import pl.adrianczerwinski.domain.validation.ValidatePostalCodeUseCase
import pl.adrianczerwinski.domain.validation.ValidateSimpleInputUseCase
import pl.adrianczerwinski.prostafaktura.core.ui.R
import pl.adrianczerwinski.ui.models.TextFieldState
import javax.inject.Inject

data class AddClientUiState (
    val email: TextFieldState = TextFieldState(),
    val phoneNumber: TextFieldState = TextFieldState(),
    val name: TextFieldState = TextFieldState(),
    val taxNumber: TextFieldState = TextFieldState(),
    val streetAndNumber: TextFieldState = TextFieldState(),
    val city: TextFieldState = TextFieldState(),
    val postalCode: TextFieldState = TextFieldState(),
    val newOthersKey: TextFieldState = TextFieldState(),
    val newOthersValue: TextFieldState = TextFieldState(),
    val others: Map<String, String> = mapOf(),
    val isError: Boolean = false,
    val screenType: ScreenType = CLIENT_DATA,
    val clientType: ClientType = COMPANY,
    val selectedClientCountry: ClientCountry? = null,
    val countryList: List<ClientCountry> = listOf(POLAND, GERMANY)
) {
    enum class ScreenType { CLIENT_DATA, CLIENT_SETTINGS }

    enum class ClientType { PERSON, COMPANY }

    enum class ClientCountry(val countryName: String) {
        POLAND (countryName = "Poland"),
        GERMANY (countryName = "Germany")
    }
}

sealed class AddClientUiAction {
    object CloseAddClientFlow : AddClientUiAction()
}

sealed class AddClientUiEvent {
    object BackPressed : AddClientUiEvent()
    object OpenClientSettingsPressed : AddClientUiEvent()
    object SavePressed : AddClientUiEvent()
    object ChooseLanguagePressed : AddClientUiEvent()
    object ChooseCurrencyPressed : AddClientUiEvent()
    object PersonTypePressed : AddClientUiEvent()
    object CompanyTypePressed : AddClientUiEvent()

    data class EmailChanged(val email: String) : AddClientUiEvent()
    data class NameChanged(val name: String) : AddClientUiEvent()
    data class TaxNumberChanged(val taxNumber: String) : AddClientUiEvent()
    data class StreetAndNumberChanged(val streetAndNumber: String) : AddClientUiEvent()
    data class CityChanged(val city: String) : AddClientUiEvent()
    data class PostalCodeChanged(val postalCode: String) : AddClientUiEvent()
    data class OthersValueChanged(val value: String) : AddClientUiEvent()
    data class OthersKeyChanged(val key: String) : AddClientUiEvent()
    data class CountrySelected(val key: Int) : AddClientUiEvent()
}

@HiltViewModel
class AddClientViewModel @Inject constructor(
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePostalCodeUseCase: ValidatePostalCodeUseCase,
    private val validateSimpleInputUseCase: ValidateSimpleInputUseCase,
    private val resourceProvider: ResourceProvider
) : StateActionsViewModel<AddClientUiState, AddClientUiAction>(AddClientUiState()) {

    fun handleUiEvent(event: AddClientUiEvent) {
        when (event) {
            is BackPressed -> onBackPressed()
            is ChooseCurrencyPressed -> TODO()
            is ChooseLanguagePressed -> TODO()
            is OpenClientSettingsPressed -> onOpenClientSettingsPressed()
            is SavePressed -> TODO()
            is CityChanged -> updateState { copy(city = TextFieldState(value = event.city)) }
            is NameChanged -> updateState { copy(name = TextFieldState(value = event.name)) }
            is EmailChanged -> updateState { copy(email = TextFieldState(value = event.email)) }
            is OthersKeyChanged -> TODO()
            is OthersValueChanged -> TODO()
            is PostalCodeChanged -> updateState { copy(postalCode = TextFieldState(value = event.postalCode)) }
            is StreetAndNumberChanged -> updateState { copy(streetAndNumber = TextFieldState(value = event.streetAndNumber)) }
            is TaxNumberChanged -> updateState { copy(taxNumber = TextFieldState(value = event.taxNumber)) }
            is CompanyTypePressed -> updateState { copy(clientType = COMPANY) }
            is PersonTypePressed -> updateState { copy(clientType = PERSON) }
            is CountrySelected -> updateState { copy(selectedClientCountry = countryList[event.key]) }
        }
    }

    private fun onBackPressed() {
        when (states.value.screenType) {
            CLIENT_DATA -> action(CloseAddClientFlow)
            CLIENT_SETTINGS -> updateState { copy(screenType = CLIENT_DATA) }
        }
    }

    private fun onOpenClientSettingsPressed() {
        val fieldsValid = validateCompanyInputs()
        if (fieldsValid) updateState { copy(screenType = CLIENT_SETTINGS) }
    }

    private fun validateCompanyInputs(): Boolean {
        val companyNameValidationResult = validateSimpleInputUseCase(states.value.name.value)
        val taxNumberValidationResult = validateSimpleInputUseCase(states.value.taxNumber.value)
        val streetValidationResult = validateSimpleInputUseCase(states.value.streetAndNumber.value)
        val cityValidationResult = validateSimpleInputUseCase(states.value.city.value)
        val postalCodeValidationResult = validatePostalCodeUseCase(states.value.postalCode.value)
        val emailValidationResult = validateEmailUseCase(states.value.email.value)

        updateState {
            copy(
                name = name.copy(
                    isError = !companyNameValidationResult,
                    errorMessage = getErrorMessage(isError = !companyNameValidationResult)
                ),
                taxNumber = taxNumber.copy(
                    isError = !taxNumberValidationResult,
                    errorMessage = getErrorMessage(isError = !taxNumberValidationResult)
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
                ),
                email = email.copy(
                    isError = !emailValidationResult,
                    errorMessage = getErrorMessage(isError = !emailValidationResult)
                )
            )
        }

        return listOf(
            companyNameValidationResult,
            taxNumberValidationResult,
            streetValidationResult,
            cityValidationResult,
            postalCodeValidationResult,
            emailValidationResult
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
