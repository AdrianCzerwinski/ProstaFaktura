package pl.adrianczerwinski.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import pl.adrianczerwinski.ui.ColumnPreview
import pl.adrianczerwinski.ui.LightDarkPreview
import pl.adrianczerwinski.ui.components.SpacerType.HORIZONTAL

@Composable
fun InfoRow(
    key: String,
    value: String,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    textColor: Color = MaterialTheme.colorScheme.onBackground
) = Row(modifier = modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
    Text(text = "$key:", style = textStyle, color = textColor)
    SpacerLarge(HORIZONTAL)
    Text(text = value, style = textStyle, color = textColor)
}

@LightDarkPreview
@Composable
private fun InfoRowPreview() = ColumnPreview(modifier = Modifier.padding(24.dp)) {
    InfoRow(key = "Key", value = "Value")
}
