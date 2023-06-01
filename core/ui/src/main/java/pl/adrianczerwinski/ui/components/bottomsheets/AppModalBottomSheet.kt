package pl.adrianczerwinski.ui.components.bottomsheets

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppModalBottomSheet(
    modifier: Modifier = Modifier,
    contentColor: Color = MaterialTheme.colorScheme.background,
    containerColor: Color = MaterialTheme.colorScheme.background,
    onDismiss: () -> Unit,
    content: @Composable () -> Unit
) = ModalBottomSheet(
    modifier = modifier,
    contentColor = contentColor,
    containerColor = containerColor,
    onDismissRequest = onDismiss
) {
    content()
}
