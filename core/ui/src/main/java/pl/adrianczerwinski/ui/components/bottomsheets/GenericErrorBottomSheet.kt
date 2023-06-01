package pl.adrianczerwinski.ui.components.bottomsheets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import pl.adrianczerwinski.prostafaktura.core.ui.R
import pl.adrianczerwinski.ui.components.ElevatedIconButton
import pl.adrianczerwinski.ui.components.SpacerLarge

@Composable
fun GenericErrorBottomSheet(onDismiss: () -> Unit, onButtonClick: () -> Unit) = AppModalBottomSheet(onDismiss = onDismiss) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(40.dp)
    ) {
        Icon(imageVector = Icons.Default.Close, contentDescription = "Error icon", modifier = Modifier.size(48.dp))
        SpacerLarge()
        Text(text = stringResource(R.string.generic_error_message), textAlign = TextAlign.Center)
        SpacerLarge()
        ElevatedIconButton(text = stringResource(R.string.generic_error_button_label)) { onButtonClick() }
    }
}
