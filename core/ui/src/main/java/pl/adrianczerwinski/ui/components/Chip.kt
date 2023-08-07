package pl.adrianczerwinski.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun RowScope.Chip(
    isSelected: Boolean,
    text: String,
    selectedContainerColor: Color = MaterialTheme.colorScheme.primary,
    containerColor: Color = MaterialTheme.colorScheme.onSecondary,
    textColor: Color = MaterialTheme.colorScheme.onBackground,
    selectedTextColor: Color = MaterialTheme.colorScheme.onPrimary,
    onClick: () -> Unit = {}
) = Surface(
    modifier = Modifier
        .weight(1f)
        .clickable { onClick() },
    shadowElevation = if (isSelected) 8.dp else 0.dp,
    color = if (isSelected) selectedContainerColor else containerColor
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier.padding(8.dp),
            text = text,
            color = if (isSelected) selectedTextColor else textColor,
            style = MaterialTheme.typography.labelMedium
        )
    }
}
