package pl.adrianczerwinski.addclient

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import pl.adrianczerwinski.addclient.AddClientUiAction.CloseAddClientFlow
import pl.adrianczerwinski.addclient.AddClientUiEvent.BackPressed
import pl.adrianczerwinski.addclient.AddClientUiEvent.CityChanged
import pl.adrianczerwinski.addclient.AddClientUiEvent.NameChanged
import pl.adrianczerwinski.addclient.AddClientUiEvent.CompanyTypePressed
import pl.adrianczerwinski.addclient.AddClientUiEvent.CountrySelected
import pl.adrianczerwinski.addclient.AddClientUiEvent.EmailChanged
import pl.adrianczerwinski.addclient.AddClientUiEvent.OpenClientSettingsPressed
import pl.adrianczerwinski.addclient.AddClientUiEvent.PersonTypePressed
import pl.adrianczerwinski.addclient.AddClientUiEvent.PostalCodeChanged
import pl.adrianczerwinski.addclient.AddClientUiEvent.SavePressed
import pl.adrianczerwinski.addclient.AddClientUiEvent.StreetAndNumberChanged
import pl.adrianczerwinski.addclient.AddClientUiEvent.TaxNumberChanged
import pl.adrianczerwinski.addclient.AddClientUiState.ClientCountry
import pl.adrianczerwinski.addclient.AddClientUiState.ClientType
import pl.adrianczerwinski.addclient.AddClientUiState.ClientType.COMPANY
import pl.adrianczerwinski.addclient.AddClientUiState.ClientType.PERSON
import pl.adrianczerwinski.addclient.AddClientUiState.ScreenType.CLIENT_DATA
import pl.adrianczerwinski.addclient.AddClientUiState.ScreenType.CLIENT_SETTINGS
import pl.adrianczerwinski.common.BackPressHandler
import pl.adrianczerwinski.common.HandleAction
import pl.adrianczerwinski.prostafaktura.features.addclient.R
import pl.adrianczerwinski.ui.ScreenLightDarkPreview
import pl.adrianczerwinski.ui.ScreenPreview
import pl.adrianczerwinski.ui.components.AppTextField
import pl.adrianczerwinski.ui.components.AppTopBar
import pl.adrianczerwinski.ui.components.BottomBarWithButton
import pl.adrianczerwinski.ui.components.SelectionDropDown
import pl.adrianczerwinski.ui.components.SpacerLarge
import pl.adrianczerwinski.ui.components.SpacerMedium
import pl.adrianczerwinski.ui.components.SpacerType
import pl.adrianczerwinski.ui.models.TextFieldState
import pl.adrianczerwinski.prostafaktura.core.ui.R as uiR

@Composable
fun AddClient(
    navigation: AddClientNavigation,
    viewModel: AddClientViewModel = hiltViewModel()
) {
    val uiState by viewModel.states.collectAsStateWithLifecycle()

    BackPressHandler { viewModel.handleUiEvent(BackPressed) }

    AddClientScreen(uiState = uiState, uiEvent = viewModel::handleUiEvent)

    HandleAction(viewModel.actions) { action ->
        when (action) {
            CloseAddClientFlow -> navigation.closeAddClientFlow()
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun AddClientScreen(
    uiState: AddClientUiState,
    uiEvent: (AddClientUiEvent) -> Unit = {}
) {
    val focusManager = LocalFocusManager.current

    Scaffold(
        topBar = { AppTopBar(title = stringResource(R.string.add_client_top_bar_title)) { uiEvent(BackPressed) } },
        bottomBar = {
            BottomBarWithButton(
                buttonText = stringResource(R.string.go_to_settings),
                textStyle = MaterialTheme.typography.labelMedium
            ) {
                focusManager.clearFocus()
                when (uiState.screenType) {
                    CLIENT_DATA -> uiEvent(OpenClientSettingsPressed)
                    CLIENT_SETTINGS -> uiEvent(SavePressed)
                }
            }
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            SpacerLarge()
            if (uiState.screenType == CLIENT_DATA) ClientTypePicker(uiEvent = uiEvent, clientType = uiState.clientType)
            ElevatedCard(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth(),
                elevation = CardDefaults.elevatedCardElevation(defaultElevation = 8.dp)
            ) {
                AnimatedContent(uiState.screenType, label = "") {
                    when (uiState.screenType) {
                        CLIENT_DATA -> {
                            DataFormFields(
                                companyName = uiState.name,
                                taxNumber = uiState.taxNumber,
                                city = uiState.city,
                                streetAndNumber = uiState.streetAndNumber,
                                postalCode = uiState.postalCode,
                                uiEvent = uiEvent,
                                email = uiState.email,
                                clientType = uiState.clientType
                            )
                        }

                        CLIENT_SETTINGS -> ClientSettings(uiEvent = uiEvent, countries = uiState.countryList)
                    }
                }
            }
        }
    }
}

@Composable
private fun DataFormFields(
    companyName: TextFieldState,
    taxNumber: TextFieldState,
    city: TextFieldState,
    streetAndNumber: TextFieldState,
    postalCode: TextFieldState,
    uiEvent: (AddClientUiEvent) -> Unit,
    email: TextFieldState,
    clientType: ClientType
) = Column(
    modifier = Modifier
        .fillMaxWidth(),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center
) {
    val focusManager = LocalFocusManager.current
    SpacerMedium()

    AppTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp, start = 12.dp, end = 12.dp),
        state = companyName,
        onValueChange = { uiEvent(NameChanged(it)) },
        placeholder = stringResource(
            when (clientType) {
                PERSON -> uiR.string.full_name
                COMPANY -> uiR.string.name
            }
        ),
        label = stringResource(
            when (clientType) {
                PERSON -> uiR.string.name_placeholder
                COMPANY -> uiR.string.company_name_placeholder
            }
        ),
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
        textStyle = MaterialTheme.typography.bodyMedium
    )

    AnimatedVisibility(clientType == COMPANY) {
        AppTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp, start = 12.dp, end = 12.dp),
            state = taxNumber,
            onValueChange = { uiEvent(TaxNumberChanged(it)) },
            placeholder = stringResource(uiR.string.tax_number),
            label = stringResource(uiR.string.tax_number),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
            textStyle = MaterialTheme.typography.bodyMedium
        )
        SpacerMedium()
    }

    AppTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp, start = 12.dp, end = 12.dp),
        state = city,
        onValueChange = { uiEvent(CityChanged(it)) },
        placeholder = stringResource(uiR.string.city),
        label = stringResource(uiR.string.city_placeholder),
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
        textStyle = MaterialTheme.typography.bodyMedium
    )

    AppTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp, start = 12.dp, end = 12.dp),
        state = streetAndNumber,
        onValueChange = { uiEvent(StreetAndNumberChanged(it)) },
        placeholder = stringResource(uiR.string.street_name),
        label = stringResource(uiR.string.street_name_placeholder),
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
        textStyle = MaterialTheme.typography.bodyMedium
    )

    AppTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp, start = 12.dp, end = 12.dp),
        state = postalCode,
        onValueChange = { uiEvent(PostalCodeChanged(it)) },
        placeholder = stringResource(uiR.string.postal_code),
        label = stringResource(uiR.string.postal_code_placeholder),
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
        textStyle = MaterialTheme.typography.bodyMedium
    )

    AppTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp, start = 12.dp, end = 12.dp),
        state = email,
        onValueChange = { uiEvent(EmailChanged(it)) },
        placeholder = stringResource(uiR.string.email),
        label = stringResource(uiR.string.email_placeholder),
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
        textStyle = MaterialTheme.typography.bodyMedium
    )

    SpacerMedium()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ClientTypePicker(
    uiEvent: (AddClientUiEvent) -> Unit,
    clientType: ClientType
) = Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
    FilterChip(
        colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = MaterialTheme.colorScheme.onSecondaryContainer,
            selectedLabelColor = MaterialTheme.colorScheme.background
        ),
        selected = clientType == PERSON,
        onClick = { uiEvent(PersonTypePressed) },
        label = { Text(text = "Osoba prywatna", style = MaterialTheme.typography.labelMedium) },
        elevation = FilterChipDefaults.elevatedFilterChipElevation()
    )
    SpacerMedium(SpacerType.HORIZONTAL)
    FilterChip(
        colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = MaterialTheme.colorScheme.onSecondaryContainer,
            selectedLabelColor = MaterialTheme.colorScheme.background
        ),
        selected = clientType == COMPANY,
        onClick = { uiEvent(CompanyTypePressed) },
        label = { Text(text = "Firma", style = MaterialTheme.typography.labelMedium) })
}

@Composable
private fun ClientSettings(
    uiEvent: (AddClientUiEvent) -> Unit,
    countries: List<ClientCountry>,
) {
    SelectionDropDown(
        label = stringResource(R.string.select_country),
        items = countries.map { it.countryName }.toTypedArray()
    ) {
        uiEvent(CountrySelected(it))
    }
}

@ScreenLightDarkPreview
@Composable
private fun ClientScreenPreview() = ScreenPreview {
    AddClientScreen(AddClientUiState())
}