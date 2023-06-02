package pl.adrianczerwinski.ui.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import pl.adrianczerwinski.ui.ColumnPreview
import pl.adrianczerwinski.ui.LightDarkPreview
import pl.adrianczerwinski.ui.models.TextFieldState

@Suppress("LongParameterList")
@Composable
fun AppTextField(
    state: TextFieldState,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = LocalTextStyle.current,
    label: String? = null,
    placeholder: String? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    prefix: @Composable (() -> Unit)? = null,
    suffix: @Composable (() -> Unit)? = null,
    supportingText: @Composable (() -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = TextFieldDefaults.shape,
    colors: TextFieldColors = getTextFieldColors()
) = Column {
    TextField(
        value = state.value,
        onValueChange = onValueChange,
        modifier = modifier,
        enabled = enabled,
        readOnly = readOnly,
        textStyle = textStyle,
        label = { label?.let { Text(text = it) } },
        placeholder = { placeholder?.let { Text(text = it) } },
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        prefix = prefix,
        suffix = suffix,
        supportingText = supportingText,
        isError = state.isError,
        visualTransformation = visualTransformation,
        keyboardActions = keyboardActions,
        keyboardOptions = keyboardOptions,
        maxLines = maxLines,
        minLines = minLines,
        interactionSource = interactionSource,
        shape = shape,
        colors = colors,
        singleLine = singleLine
    )
    state.errorMessage?.let { Text(text = it, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.error) }
}

@Suppress("LongParameterList")
@Composable
fun OutlinedAppTextField(
    state: TextFieldState,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = LocalTextStyle.current,
    label: String? = null,
    placeholder: String? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    prefix: @Composable (() -> Unit)? = null,
    suffix: @Composable (() -> Unit)? = null,
    supportingText: @Composable (() -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = OutlinedTextFieldDefaults.shape,
    colors: TextFieldColors = getOutlinedTextFieldColors()
) = Column {
    OutlinedTextField(
        value = state.value,
        onValueChange = onValueChange,
        modifier = modifier,
        enabled = enabled,
        readOnly = readOnly,
        textStyle = textStyle,
        label = { label?.let { Text(text = it) } },
        placeholder = { placeholder?.let { Text(text = it) } },
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        prefix = prefix,
        suffix = suffix,
        supportingText = supportingText,
        isError = state.isError,
        visualTransformation = visualTransformation,
        keyboardActions = keyboardActions,
        keyboardOptions = keyboardOptions,
        maxLines = maxLines,
        minLines = minLines,
        interactionSource = interactionSource,
        shape = shape,
        colors = colors,
        singleLine = singleLine
    )
    state.errorMessage?.let { Text(text = it, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.error) }
}

@Composable
private fun getTextFieldColors() = TextFieldDefaults.colors(
    focusedTextColor = MaterialTheme.colorScheme.background,
    disabledTextColor = MaterialTheme.colorScheme.background,
    focusedSupportingTextColor = MaterialTheme.colorScheme.background,
    disabledSupportingTextColor = MaterialTheme.colorScheme.background,
    unfocusedTextColor = MaterialTheme.colorScheme.background,
    cursorColor = MaterialTheme.colorScheme.background,
    unfocusedContainerColor = MaterialTheme.colorScheme.primary,
    focusedContainerColor = MaterialTheme.colorScheme.primary,
    errorContainerColor = MaterialTheme.colorScheme.primary,
    errorTextColor = MaterialTheme.colorScheme.background,
    disabledPlaceholderColor = MaterialTheme.colorScheme.tertiary,
    errorPlaceholderColor = MaterialTheme.colorScheme.tertiary,
    focusedPlaceholderColor = MaterialTheme.colorScheme.tertiary,
    unfocusedPlaceholderColor = MaterialTheme.colorScheme.tertiary,
    disabledLabelColor = MaterialTheme.colorScheme.background,
    errorLabelColor = MaterialTheme.colorScheme.background,
    focusedLabelColor = MaterialTheme.colorScheme.background,
    unfocusedLabelColor = MaterialTheme.colorScheme.background
)

@Composable
private fun getOutlinedTextFieldColors() = OutlinedTextFieldDefaults.colors(
    focusedTextColor = MaterialTheme.colorScheme.onBackground,
    disabledTextColor = MaterialTheme.colorScheme.onBackground,
    focusedSupportingTextColor = MaterialTheme.colorScheme.onBackground,
    disabledSupportingTextColor = MaterialTheme.colorScheme.onBackground,
    unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
    cursorColor = MaterialTheme.colorScheme.onBackground,
    unfocusedContainerColor = MaterialTheme.colorScheme.background,
    focusedContainerColor = MaterialTheme.colorScheme.background,
    errorContainerColor = MaterialTheme.colorScheme.background,
    errorTextColor = MaterialTheme.colorScheme.onBackground,
    disabledPlaceholderColor = MaterialTheme.colorScheme.onBackground,
    errorPlaceholderColor = MaterialTheme.colorScheme.onBackground,
    focusedPlaceholderColor = MaterialTheme.colorScheme.onBackground,
    unfocusedPlaceholderColor = MaterialTheme.colorScheme.onBackground,
    disabledLabelColor = MaterialTheme.colorScheme.onBackground,
    errorLabelColor = MaterialTheme.colorScheme.onBackground,
    focusedLabelColor = MaterialTheme.colorScheme.onBackground,
    unfocusedLabelColor = MaterialTheme.colorScheme.onBackground,
    disabledBorderColor = MaterialTheme.colorScheme.onBackground,
    errorBorderColor = MaterialTheme.colorScheme.error,
    focusedBorderColor = MaterialTheme.colorScheme.onBackground,
    unfocusedBorderColor = MaterialTheme.colorScheme.onBackground
)

@LightDarkPreview
@Composable
private fun AppTextFieldPreview() = ColumnPreview(modifier = Modifier.padding(24.dp)) {
    AppTextField(state = TextFieldState(value = ""), onValueChange = {}, label = "Label", placeholder = "Email")
    SpacerMedium()
    AppTextField(state = TextFieldState(value = "Sample", isError = true, errorMessage = "Error"), onValueChange = {})
    SpacerMedium()
    OutlinedAppTextField(state = TextFieldState(value = "Sample", isError = true, errorMessage = "Error"), onValueChange = {})
    SpacerMedium()
    OutlinedAppTextField(state = TextFieldState(value = "Sample"), onValueChange = {})
    SpacerMedium()
    OutlinedAppTextField(state = TextFieldState(value = "Sample"), onValueChange = {})
}
