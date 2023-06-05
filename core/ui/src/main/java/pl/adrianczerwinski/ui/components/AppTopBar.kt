package pl.adrianczerwinski.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import pl.adrianczerwinski.ui.ColumnPreview
import pl.adrianczerwinski.ui.LightDarkPreview

@Composable
fun AppTopBar(
    title: String,
    modifier: Modifier = Modifier,
    leadingIcon: ImageVector = Icons.Filled.ArrowBack,
    onClick: () -> Unit = {}
) = Surface(modifier = Modifier.fillMaxWidth(), shadowElevation = 4.dp) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier
                .clip(CircleShape)
                .clickable { onClick() }
                .padding(12.dp)
                .size(32.dp),
            imageVector = leadingIcon,
            contentDescription = "Back Arrow Icon"
        )
        SpacerLarge(SpacerType.HORIZONTAL)
        Text(text = title, style = MaterialTheme.typography.labelMedium)
    }
}

@LightDarkPreview
@Composable
private fun AppTopBarPreview() = ColumnPreview {
    AppTopBar(title = "Preview Top Bar")
}
