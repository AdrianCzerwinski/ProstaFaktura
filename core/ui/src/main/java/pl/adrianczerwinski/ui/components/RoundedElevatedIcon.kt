package pl.adrianczerwinski.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import pl.adrianczerwinski.ui.ColumnPreview
import pl.adrianczerwinski.ui.LightDarkPreview

@Composable
fun RoundedElevatedIcon(
    imageVector: ImageVector,
    size: Dp = 24.dp,
    tint: Color = MaterialTheme.colorScheme.secondary,
    onClick: () -> Unit = {}
) {
    Surface(
        modifier = Modifier
            .clickable { onClick() }
            .size(size = size)
            .padding(2.dp)
            .clip(CircleShape)
            .border(width = 1.dp, color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f), shape = CircleShape)
            .shadow(elevation = 4.dp),
        shadowElevation = 3.dp
    ) {
        Icon(
            modifier = Modifier.size(size = size.times(ICON_SIZE_RATIO)),
            imageVector = imageVector,
            contentDescription = "Icon",
            tint = tint
        )
    }
}

@LightDarkPreview
@Composable
private fun RoundedElevatedIconPreview() = ColumnPreview {
    RoundedElevatedIcon(Icons.Filled.Close)
}

private const val ICON_SIZE_RATIO = 0.8f
