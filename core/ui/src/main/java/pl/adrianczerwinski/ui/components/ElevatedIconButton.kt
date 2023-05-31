package pl.adrianczerwinski.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterEnd
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pl.adrianczerwinski.ui.theme.ProstaFakturaTheme

@Composable
fun ElevatedIconButton(
    text: String,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.labelLarge,
    icon: ImageVector? = null,
    onClick: () -> Unit = {}
) = ElevatedButton(
    modifier = modifier
        .padding(16.dp)
        .fillMaxWidth(),
    onClick = onClick,
    contentPadding = PaddingValues(12.dp)
) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Text(modifier = Modifier.align(Center), text = text, style = textStyle, color = MaterialTheme.colorScheme.onBackground)
        icon?.let {
            Icon(
                modifier = Modifier
                    .size(32.dp)
                    .align(CenterEnd),
                imageVector = it,
                contentDescription = "Forward Arrow",
                tint = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

@Preview
@Composable
fun ElevatedIconButtonPreview() = ProstaFakturaTheme(dynamicColor = false) {
    ElevatedIconButton(text = "Sample", icon = Icons.Default.AccountCircle)
}
