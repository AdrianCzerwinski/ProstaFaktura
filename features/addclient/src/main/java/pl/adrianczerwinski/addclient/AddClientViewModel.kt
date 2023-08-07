package pl.adrianczerwinski.addclient

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import pl.adrianczerwinski.addclient.AddClientUiAction.CloseAddClientFlow
import pl.adrianczerwinski.addclient.AddClientUiAction.CloseAddClientFlowWithSuccess
import pl.adrianczerwinski.addclient.AddClientUiEvent.BackPressed
import pl.adrianczerwinski.addclient.AddClientUiEvent.CityChanged
import pl.adrianczerwinski.addclient.AddClientUiEvent.CloseBottomSheet
import pl.adrianczerwinski.addclient.AddClientUiEvent.CompanyTypePressed
import pl.adrianczerwinski.addclient.AddClientUiEvent.CountrySelected
import pl.adrianczerwinski.addclient.AddClientUiEvent.CurrencySelected
import pl.adrianczerwinski.addclient.AddClientUiEvent.DeleteOtherInfo
import pl.adrianczerwinski.addclient.AddClientUiEvent.EmailChanged
import pl.adrianczerwinski.addclient.AddClientUiEvent.LanguageSelected
import pl.adrianczerwinski.addclient.AddClientUiEvent.NameChanged
import pl.adrianczerwinski.addclient.AddClientUiEvent.OnAddOthersPressed
import pl.adrianczerwinski.addclient.AddClientUiEvent.OpenClientSettingsPressed
import pl.adrianczerwinski.addclient.AddClientUiEvent.OthersKeyChanged
import pl.adrianczerwinski.addclient.AddClientUiEvent.OthersValueChanged
import pl.adrianczerwinski.addclient.AddClientUiEvent.PersonTypePressed
import pl.adrianczerwinski.addclient.AddClientUiEvent.PostalCodeChanged
import pl.adrianczerwinski.addclient.AddClientUiEvent.SaveOthersPressed
import pl.adrianczerwinski.addclient.AddClientUiEvent.SavePressed
import pl.adrianczerwinski.addclient.AddClientUiEvent.StreetAndNumberChanged
import pl.adrianczerwinski.addclient.AddClientUiEvent.TaxNumberChanged
import pl.adrianczerwinski.addclient.AddClientUiEvent.TryAgainPressed
import pl.adrianczerwinski.addclient.AddClientUiState.BottomSheet.Error
import pl.adrianczerwinski.addclient.AddClientUiState.BottomSheet.Others
import pl.adrianczerwinski.addclient.AddClientUiState.ClientCountry
import pl.adrianczerwinski.addclient.AddClientUiState.ClientCountry.GERMANY
import pl.adrianczerwinski.addclient.AddClientUiState.ClientCountry.POLAND
import pl.adrianczerwinski.addclient.AddClientUiState.ClientCurrency
import pl.adrianczerwinski.addclient.AddClientUiState.ClientCurrency.EUR
import pl.adrianczerwinski.addclient.AddClientUiState.ClientCurrency.PLN
import pl.adrianczerwinski.addclient.AddClientUiState.ClientLanguage
import pl.adrianczerwinski.addclient.AddClientUiState.ClientLanguage.GERMAN
import pl.adrianczerwinski.addclient.AddClientUiState.ClientLanguage.POLISH
import pl.adrianczerwinski.addclient.AddClientUiState.ClientType.COMPANY
import pl.adrianczerwinski.addclient.AddClientUiState.ClientType.PERSON
import pl.adrianczerwinski.addclient.AddClientUiState.ScreenType.CLIENT_DATA
import pl.adrianczerwinski.addclient.AddClientUiState.ScreenType.CLIENT_SETTINGS
import pl.adrianczerwinski.client.model.Client
import pl.adrianczerwinski.common.StateActionsViewModel
import pl.adrianczerwinski.common.action
import pl.adrianczerwinski.common.resourceprovider.ResourceProvider
import pl.adrianczerwinski.domain.client.SaveClientUseCase
import pl.adrianczerwinski.domain.validation.ValidateEmailUseCase
import pl.adrianczerwinski.domain.validation.ValidatePostalCodeUseCase
import pl.adrianczerwinski.domain.validation.ValidateSimpleInputUseCase
import pl.adrianczerwinski.prostafaktura.features.addclient.R
import pl.adrianczerwinski.ui.models.TextFieldState
import javax.inject.Inject
import pl.adrianczerwinski.prostafaktura.core.ui.R as uiR

data class AddClientUiState(
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
    val selectedLanguage: ClientLanguage? = null,
    val selectedCurrency: ClientCurrency? = null,
    val countryList: List<ClientCountry> = listOf(POLAND, GERMANY),
    val currencyList: List<ClientCurrency> = listOf(PLN, EUR),
    val languageList: List<ClientLanguage> = listOf(POLISH, GERMAN),
    val bottomSheet: BottomSheet? = null
) {
    enum class ScreenType { CLIENT_DATA, CLIENT_SETTINGS }

    enum class ClientType { PERSON, COMPANY }

    enum class ClientCountry { POLAND, GERMANY }
    enum class ClientLanguage { POLISH, GERMAN }
    enum class ClientCurrency { PLN, EUR }

    sealed class BottomSheet {
        object Others : BottomSheet()
        object Error : BottomSheet()
    }
}

sealed class AddClientUiAction {
    data class CloseAddClientFlowWithSuccess(val message: String) : AddClientUiAction()
    object CloseAddClientFlow : AddClientUiAction()
}

sealed class AddClientUiEvent {
    object BackPressed : AddClientUiEvent()
    object OpenClientSettingsPressed : AddClientUiEvent()
    object SavePressed : AddClientUiEvent()
    object SaveOthersPressed : AddClientUiEvent()
    object PersonTypePressed : AddClientUiEvent()
    object CompanyTypePressed : AddClientUiEvent()
    object CloseBottomSheet : AddClientUiEvent()
    object TryAgainPressed : AddClientUiEvent()
    object OnAddOthersPressed : AddClientUiEvent()

    data class EmailChanged(val email: String) : AddClientUiEvent()
    data class NameChanged(val name: String) : AddClientUiEvent()
    data class TaxNumberChanged(val taxNumber: String) : AddClientUiEvent()
    data class StreetAndNumberChanged(val streetAndNumber: String) : AddClientUiEvent()
    data class CityChanged(val city: String) : AddClientUiEvent()
    data class PostalCodeChanged(val postalCode: String) : AddClientUiEvent()
    data class OthersValueChanged(val value: String) : AddClientUiEvent()
    data class OthersKeyChanged(val key: String) : AddClientUiEvent()
    data class CountrySelected(val country: ClientCountry) : AddClientUiEvent()
    data class CurrencySelected(val currency: ClientCurrency) : AddClientUiEvent()
    data class LanguageSelected(val language: ClientLanguage) : AddClientUiEvent()
    data class DeleteOtherInfo(val otherInfoKey: String) : AddClientUiEvent()
}

@HiltViewModel
class AddClientViewModel @Inject constructor(
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePostalCodeUseCase: ValidatePostalCodeUseCase,
    private val validateSimpleInputUseCase: ValidateSimpleInputUseCase,
    private val resourceProvider: ResourceProvider,
    private val saveClientUseCase: SaveClientUseCase
) : StateActionsViewModel<AddClientUiState, AddClientUiAction>(AddClientUiState()) {

    @Suppress("CyclomaticComplexMethod")
    fun handleUiEvent(event: AddClientUiEvent) {
        when (event) {
            is BackPressed -> onBackPressed()
            is OpenClientSettingsPressed -> onOpenClientSettingsPressed()
            is SavePressed -> onSavePressed()
            is CityChanged -> updateState { copy(city = TextFieldState(value = event.city)) }
            is NameChanged -> updateState { copy(name = TextFieldState(value = event.name)) }
            is EmailChanged -> updateState { copy(email = TextFieldState(value = event.email)) }
            is OthersValueChanged -> updateState { copy(newOthersValue = TextFieldState(value = event.value)) }
            is OthersKeyChanged -> updateState { copy(newOthersKey = TextFieldState(value = event.key)) }
            is PostalCodeChanged -> updateState { copy(postalCode = TextFieldState(value = event.postalCode)) }
            is StreetAndNumberChanged -> updateState { copy(streetAndNumber = TextFieldState(value = event.streetAndNumber)) }
            is TaxNumberChanged -> updateState { copy(taxNumber = TextFieldState(value = event.taxNumber)) }
            is CompanyTypePressed -> updateState { copy(clientType = COMPANY) }
            is PersonTypePressed -> updateState { copy(clientType = PERSON) }
            is CountrySelected -> updateState { copy(selectedClientCountry = event.country) }
            is LanguageSelected -> updateState { copy(selectedLanguage = event.language) }
            is CloseBottomSheet -> updateState { copy(bottomSheet = null) }
            is OnAddOthersPressed -> updateState { copy(bottomSheet = Others) }
            is SaveOthersPressed -> onOthersChanged()
            is TryAgainPressed -> onTryAgainPressed()
            is CurrencySelected -> updateState { copy(selectedCurrency = event.currency) }
            is DeleteOtherInfo -> onDeleteOtherInfo(otherInfoKey = event.otherInfoKey)
        }
    }

    private fun onDeleteOtherInfo(otherInfoKey: String) {
        val newOthers = states.value.others.filterNot { it.key == otherInfoKey }
        updateState { copy(others = newOthers) }
    }

    private fun onSavePressed() = viewModelScope.launch {
        val client = with(states.value) {
            Client(
                name = name.value,
                taxNumber = taxNumber.value,
                language = selectedLanguage?.name.orEmpty(),
                country = selectedClientCountry?.name.orEmpty(),
                currency = selectedCurrency?.name.orEmpty(),
                city = city.value,
                streetAndNumber = streetAndNumber.value,
                postalCode = postalCode.value,
                emails = if (email.value.isNotEmpty()) listOf(email.value) else null,
                phoneNumber = phoneNumber.value
            )
        }
        val result = saveClientUseCase(client)
        if (result.isSuccess) {
            action(CloseAddClientFlow)
        } else {
            updateState { copy(bottomSheet = Error) }
        }
    }

    private fun onTryAgainPressed() {
        updateState { copy(bottomSheet = null) }
        onSavePressed()
    }

    private fun onBackPressed() {
        when (states.value.screenType) {
            CLIENT_DATA -> action(CloseAddClientFlowWithSuccess(resourceProvider.getString(R.string.successfully_added_client)))
            CLIENT_SETTINGS -> updateState { copy(screenType = CLIENT_DATA) }
        }
    }

    private fun onOpenClientSettingsPressed() {
        val fieldsValid = validateCompanyInputs()
        if (fieldsValid) updateState { copy(screenType = CLIENT_SETTINGS) }
    }

    private fun validateCompanyInputs(): Boolean {
        val companyNameValidationResult = validateSimpleInputUseCase(states.value.name.value)
        val streetValidationResult = validateSimpleInputUseCase(states.value.streetAndNumber.value)
        val cityValidationResult = validateSimpleInputUseCase(states.value.city.value)
        val postalCodeValidationResult = validatePostalCodeUseCase(states.value.postalCode.value)
        val emailValidationResult = validateEmailUseCase(email = states.value.email.value, canBeEmpty = true)

        val taxNumberValidationResult = if (states.value.clientType == COMPANY) {
            validateSimpleInputUseCase(states.value.taxNumber.value)
        } else {
            true
        }

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
                    newOthersValue = TextFieldState(),
                    bottomSheet = null
                )
            }
        } else {
            updateState {
                copy(
                    newOthersKey = newOthersKey.copy(
                        isError = true,
                        errorMessage = resourceProvider.getString(uiR.string.error_value_message)
                    ),
                    newOthersValue = newOthersValue.copy(
                        isError = true,
                        errorMessage = resourceProvider.getString(uiR.string.error_value_message)
                    ),
                )
            }
        }
    }

    private fun getErrorMessage(isError: Boolean) = if (isError) resourceProvider.getString(uiR.string.error_value_message) else null
}
