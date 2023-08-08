package pl.adrianczerwinski.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import pl.adrianczerwinski.ui.components.SpacerSmall
import pl.adrianczerwinski.ui.components.SpacerType
import pl.adrianczerwinski.ui.components.SpacerXSmall

@Composable
fun IconListItemRow(
    title: String,
    actionTitle: String,
    actionIcon: @Composable () -> Unit = {
        Icon(
            modifier = Modifier.size(24.dp),
            imageVector = Icons.Filled.KeyboardArrowRight,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
        )
    },
    subTitle: String? = null,
    leadIcon: Painter? = null,
    onClick: () -> Unit = {}
) = Column {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 24.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(modifier = Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically) {
            leadIcon?.let { Icon(painter = it, contentDescription = null, tint = MaterialTheme.colorScheme.primary) }
            SpacerXSmall(SpacerType.HORIZONTAL)
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(text = title, style = MaterialTheme.typography.labelMedium)
                subTitle?.let { Text(text = it, style = MaterialTheme.typography.bodySmall) }
            }
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = actionTitle, style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.primary))
            SpacerSmall(SpacerType.HORIZONTAL)
            actionIcon()
        }
    }
    Divider(color = MaterialTheme.colorScheme.primary)
}

@LightDarkPreview
@Composable
private fun IconListItemRowPreview() = ColumnPreview {
    IconListItemRow(title = "Example", actionTitle = "action")
    SpacerSmall()
}
