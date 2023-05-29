package pl.adrianczerwinski.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import pl.adrianczerwinski.ui.components.SpacerType.VERTICAL

enum class SpacerType { VERTICAL, HORIZONTAL }

@Composable
fun SpacerXXLarge(type: SpacerType = VERTICAL) = Spacer(
    modifier = if (type == VERTICAL) Modifier.height(48.dp) else Modifier.width(24.dp)
)
@Composable
fun SpacerXLarge(type: SpacerType = VERTICAL) = Spacer(
    modifier = if (type == VERTICAL) Modifier.height(24.dp) else Modifier.width(24.dp)
)
@Composable
fun SpacerLarge(type: SpacerType = VERTICAL) = Spacer(
    modifier = if (type == VERTICAL) Modifier.height(16.dp) else Modifier.width(16.dp)
)

@Composable
fun SpacerMedium(type: SpacerType = VERTICAL) = Spacer(
    modifier = if (type == VERTICAL) Modifier.height(12.dp) else Modifier.width(12.dp)
)

@Composable
fun SpacerSmall(type: SpacerType = VERTICAL) = Spacer(
    modifier = if (type == VERTICAL) Modifier.height(8.dp) else Modifier.width(8.dp)
)

@Composable
fun SpacerXSmall(type: SpacerType = VERTICAL) = Spacer(
    modifier = if (type == VERTICAL) Modifier.height(4.dp) else Modifier.width(4.dp)
)
