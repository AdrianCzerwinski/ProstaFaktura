package pl.adrianczerwinski.onboarding.signin

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import pl.adrianczerwinski.common.HandleAction
import pl.adrianczerwinski.onboarding.OnboardingFeatureNavigation
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
import pl.adrianczerwinski.onboarding.signin.SignInUiState.BottomSheet
import pl.adrianczerwinski.onboarding.signin.SignInUiState.BottomSheet.Error
import pl.adrianczerwinski.onboarding.signin.SignInUiState.BottomSheet.Others
import pl.adrianczerwinski.onboarding.signin.SignInUiState.ScreenType.COMPANY
import pl.adrianczerwinski.onboarding.signin.SignInUiState.ScreenType.USER
import pl.adrianczerwinski.prostafaktura.features.onboarding.R
import pl.adrianczerwinski.ui.ScreenLightDarkPreview
import pl.adrianczerwinski.ui.ScreenPreview
import pl.adrianczerwinski.ui.components.AppTextField
import pl.adrianczerwinski.ui.components.BottomBarWithButton
import pl.adrianczerwinski.ui.components.ElevatedIconButton
import pl.adrianczerwinski.ui.components.InfoRow
import pl.adrianczerwinski.ui.components.OutlinedAppTextField
import pl.adrianczerwinski.ui.components.SpacerLarge
import pl.adrianczerwinski.ui.components.SpacerMedium
import pl.adrianczerwinski.ui.components.SpacerSmall
import pl.adrianczerwinski.ui.components.SpacerType.HORIZONTAL
import pl.adrianczerwinski.ui.components.SpacerXLarge
import pl.adrianczerwinski.ui.components.bottomsheets.AppModalBottomSheet
import pl.adrianczerwinski.ui.components.bottomsheets.GenericErrorBottomSheet
import pl.adrianczerwinski.ui.models.TextFieldState
import pl.adrianczerwinski.prostafaktura.core.ui.R as uiR

@Composable
fun SignIn(
    navigation: OnboardingFeatureNavigation,
    viewModel: SignInViewModel = hiltViewModel()
) {
    val uiState by viewModel.states.collectAsStateWithLifecycle()

    SignInScreen(uiState = uiState, uiEvent = viewModel::handleUiEvent)

    HandleAction(viewModel.actions) { action ->
        when (action) {
            OpenMain -> navigation.openMainScreen()
        }
    }
}

@Composable
private fun SignInScreen(
    uiState: SignInUiState,
    uiEvent: (SignInUiEvent) -> Unit = {}
) = Scaffold(
    bottomBar = {
        BottomBarWithButton(buttonText = stringResource(R.string.confirm_info)) {
            when (uiState.screenType) {
                USER -> uiEvent(ConfirmUserInfoPressed)
                COMPANY -> uiEvent(ConfirmCompanyInfoPressed)
            }
        }
    }
) {
    BottomSheets(
        bottomSheet = uiState.bottomSheet,
        newCompanyInfoKey = uiState.newOthersKey,
        newCompanyInfoValue = uiState.newOthersValue,
        uiEvent = uiEvent
    )
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(it)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        when (uiState.screenType) {
            USER -> UserInfoForm(
                emailState = uiState.email,
                nameState = uiState.name,
                phoneNumberState = uiState.phoneNumber,
                uiEvent = uiEvent
            )

            COMPANY -> CompanyInfoForm(
                companyName = uiState.companyName,
                taxNumber = uiState.taxNumber,
                city = uiState.city,
                postalCode = uiState.postalCode,
                streetAndNumber = uiState.streetAndNumber,
                accountNumber = uiState.accountNumber,
                otherInfo = uiState.others,
                uiEvent = uiEvent
            )
        }
    }
}

@Suppress("LongMethod")
@Composable
private fun CompanyInfoForm(
    companyName: TextFieldState,
    taxNumber: TextFieldState,
    city: TextFieldState,
    streetAndNumber: TextFieldState,
    postalCode: TextFieldState,
    accountNumber: TextFieldState,
    otherInfo: Map<String, String>?,
    uiEvent: (SignInUiEvent) -> Unit
) {
    val focusManager = LocalFocusManager.current
    CompanyHeader()
    SpacerXLarge()
    AppTextField(
        modifier = Modifier.fillMaxWidth(),
        state = companyName,
        onValueChange = { uiEvent(CompanyNameChanged(it)) },
        placeholder = stringResource(uiR.string.company_name_placeholder),
        label = stringResource(uiR.string.name),
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) })
    )
    SpacerMedium()
    AppTextField(
        modifier = Modifier.fillMaxWidth(),
        state = taxNumber,
        onValueChange = { uiEvent(TaxNumberChanged(it)) },
        placeholder = stringResource(uiR.string.tax_number),
        label = stringResource(uiR.string.tax_number),
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) })
    )
    SpacerMedium()
    AppTextField(
        modifier = Modifier.fillMaxWidth(),
        state = city,
        onValueChange = { uiEvent(CityChanged(it)) },
        placeholder = stringResource(uiR.string.city),
        label = stringResource(uiR.string.city),
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) })
    )
    SpacerMedium()
    AppTextField(
        modifier = Modifier.fillMaxWidth(),
        state = streetAndNumber,
        onValueChange = { uiEvent(StreetAndNumberChanged(it)) },
        placeholder = stringResource(uiR.string.street_name),
        label = stringResource(uiR.string.street_name),
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) })
    )
    SpacerMedium()
    AppTextField(
        modifier = Modifier.fillMaxWidth(),
        state = postalCode,
        onValueChange = { uiEvent(PostalCodeChanged(it)) },
        placeholder = stringResource(uiR.string.postal_code_placeholder),
        label = stringResource(uiR.string.postal_code),
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) })
    )
    SpacerMedium()
    AppTextField(
        modifier = Modifier.fillMaxWidth(),
        state = accountNumber,
        onValueChange = { uiEvent(AccountNumberChanged(it)) },
        placeholder = stringResource(uiR.string.account_number_placeholder),
        label = stringResource(uiR.string.account_number),
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() })
    )
    otherInfo?.let { CompanyAdditionalInfo(it) }
    SpacerLarge()
    OutlinedButton(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        onClick = { uiEvent(AddOthersPressed) },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = Color.Transparent
        ),
        border = BorderStroke(width = 2.dp, color = MaterialTheme.colorScheme.tertiary)
    ) {
        Icon(imageVector = Icons.Default.Add, contentDescription = "Add Others Icon", tint = MaterialTheme.colorScheme.tertiary)
        SpacerMedium(HORIZONTAL)
        Text(
            text = stringResource(id = R.string.add_others),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.tertiary
        )
    }
}

@Composable
private fun UserInfoForm(
    nameState: TextFieldState,
    emailState: TextFieldState,
    phoneNumberState: TextFieldState,
    uiEvent: (SignInUiEvent) -> Unit
) {
    val focusManager = LocalFocusManager.current
    UserHeader()
    SpacerXLarge()
    AppTextField(
        modifier = Modifier.fillMaxWidth(),
        state = nameState,
        onValueChange = { uiEvent(NameChanged(it)) },
        placeholder = stringResource(uiR.string.name_placeholder),
        label = stringResource(uiR.string.full_name),
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions(onDone = { focusManager.moveFocus(FocusDirection.Down) })
    )
    SpacerMedium()
    AppTextField(
        modifier = Modifier.fillMaxWidth(),
        state = emailState,
        onValueChange = { uiEvent(EmailChanged(it)) },
        placeholder = stringResource(uiR.string.email_placeholder),
        label = stringResource(uiR.string.email),
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email, imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions(onDone = { focusManager.moveFocus(FocusDirection.Down) }),
        maxLines = 1
    )
    SpacerMedium()
    AppTextField(
        modifier = Modifier.fillMaxWidth(),
        state = phoneNumberState,
        onValueChange = { uiEvent(PhoneNumberChanged(it)) },
        placeholder = stringResource(uiR.string.phone_placeholder),
        label = stringResource(uiR.string.phone_number),
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = { uiEvent(ConfirmUserInfoPressed) }),
        maxLines = 1
    )
    SpacerMedium()
}

@Composable
private fun UserHeader() {
    SpacerLarge()
    Text(
        text = stringResource(R.string.user_info_header),
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.titleSmall,
        color = MaterialTheme.colorScheme.onBackground
    )
    SpacerSmall()
    Text(
        text = stringResource(R.string.user_info_disclaimer),
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.tertiary
    )
}

@Composable
private fun CompanyHeader() {
    SpacerLarge()
    Text(
        text = stringResource(R.string.user_info_header),
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.titleSmall,
        color = MaterialTheme.colorScheme.onBackground
    )
    SpacerLarge()
}

@Composable
private fun CompanyAdditionalInfo(info: Map<String, String>) {
    SpacerMedium()
    InfoRow(key = stringResource(id = uiR.string.additional_company_info_header), value = "")
    info.forEach {
        SpacerMedium()
        InfoRow(key = it.key, value = it.value)
    }
}

@Composable
private fun BottomSheets(
    bottomSheet: BottomSheet?,
    uiEvent: (SignInUiEvent) -> Unit,
    newCompanyInfoKey: TextFieldState,
    newCompanyInfoValue: TextFieldState
) {
    when (bottomSheet) {
        Error -> GenericErrorBottomSheet(
            onDismiss = { uiEvent(CloseBottomSheet) },
            onButtonClick = { uiEvent(TryAgainPressed) }
        )

        Others -> AppModalBottomSheet(onDismiss = { uiEvent(CloseBottomSheet) }) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
            ) {
                val focusManager = LocalFocusManager.current
                Text(
                    text = stringResource(uiR.string.additional_company_info_header),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
                SpacerMedium()
                OutlinedAppTextField(
                    modifier = Modifier.fillMaxWidth(),
                    state = newCompanyInfoKey,
                    onValueChange = { uiEvent(OthersKeyChanged(it)) },
                    label = stringResource(id = uiR.string.name),
                    placeholder = stringResource(id = uiR.string.name),
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
                )
                SpacerLarge()
                OutlinedAppTextField(
                    modifier = Modifier.fillMaxWidth(),
                    state = newCompanyInfoValue,
                    onValueChange = { uiEvent(OthersValueChanged(it)) },
                    label = stringResource(id = uiR.string.value),
                    placeholder = stringResource(id = uiR.string.value),
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(onNext = { focusManager.clearFocus() }),
                )
                SpacerLarge()
                ElevatedIconButton(text = stringResource(id = uiR.string.save)) { uiEvent(SaveOthersPressed) }
            }
        }

        null -> {}
    }
}

@ScreenLightDarkPreview
@Composable
private fun SignInPreview() = ScreenPreview { SignInScreen(uiState = SignInUiState()) }
