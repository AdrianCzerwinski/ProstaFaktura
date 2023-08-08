package pl.adrianczerwinski.main

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navOptions
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import pl.adrianczerwinski.common.HandleAction
import pl.adrianczerwinski.main.MainUiAction.OpenNewClient
import pl.adrianczerwinski.main.MainUiAction.OpenNewInvoice
import pl.adrianczerwinski.main.MainUiAction.OpenSettings
import pl.adrianczerwinski.main.MainUiEvent.AddButtonPressed
import pl.adrianczerwinski.main.MainUiEvent.AddClientPressed
import pl.adrianczerwinski.main.MainUiEvent.CloseBottomSheet
import pl.adrianczerwinski.main.MainUiEvent.CreateInvoicePressed
import pl.adrianczerwinski.main.MainUiEvent.SettingsPressed
import pl.adrianczerwinski.main.bottomnavigation.BottomNavRoutes
import pl.adrianczerwinski.prostafaktura.features.main.R
import pl.adrianczerwinski.ui.ScreenLightDarkPreview
import pl.adrianczerwinski.ui.ScreenPreview
import pl.adrianczerwinski.ui.components.SpacerLarge
import pl.adrianczerwinski.ui.components.SpacerType.HORIZONTAL
import pl.adrianczerwinski.ui.components.bottomsheets.AppModalBottomSheet

@Composable
fun Main(
    navigation: MainFeatureNavigation,
    viewModel: MainViewModel = hiltViewModel()
) {
    val uiState by viewModel.states.collectAsStateWithLifecycle()
    MainScreen(
        uiState = uiState,
        uiEvent = viewModel::handleUiEvent
    )

    HandleAction(viewModel.actions) { action ->
        when (action) {
            OpenNewClient -> navigation.openAddClient()
            OpenNewInvoice -> navigation.openCreateInvoice()
            OpenSettings -> navigation.openSettings()
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun MainScreen(
    uiState: MainUiState,
    uiEvent: (MainUiEvent) -> Unit = {}
) {
    val navController = rememberAnimatedNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry?.destination

    Scaffold(
        topBar = { TopBar { uiEvent(SettingsPressed) } },
        bottomBar = {
            BottomNavigationBar(
                onHomeClick = { navController.navigate(BottomNavRoutes.home, navOptions) },
                onInvoicesClick = { navController.navigate(BottomNavRoutes.invoices, navOptions) },
                currentRoute = currentDestination?.route ?: BottomNavRoutes.home
            )
        },
        floatingActionButton = { MainFloatingActionButton { uiEvent(AddButtonPressed) } },
        floatingActionButtonPosition = FabPosition.Center
    ) { padding ->
        BottomSheets(uiState.showBottomSheetPicker) { uiEvent(it) }
        AnimatedNavHost(
            navController = navController,
            modifier = Modifier.padding(padding),
            startDestination = BottomNavRoutes.home,
            enterTransition = { fadeIn(animationSpec = tween(TRANSITION_TIME)) },
            exitTransition = { fadeOut(animationSpec = tween(TRANSITION_TIME)) }
        ) {
            composable(BottomNavRoutes.home) {
                // TODO
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Home")
                }
            }
            composable(BottomNavRoutes.invoices) {
                // TODO
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Invoices")
                }
            }
        }
    }
}

@Composable
private fun BottomNavigationBar(
    onHomeClick: () -> Unit = {},
    onInvoicesClick: () -> Unit = {},
    currentRoute: String
) {
    Surface(
        modifier = Modifier,
        shadowElevation = 8.dp,
        color = MaterialTheme.colorScheme.onSecondary
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(BottomAppBarDefaults.ContentPadding),
            verticalAlignment = Alignment.CenterVertically
        ) {
            StatsBottomNavItem(currentRoute = currentRoute, onStatsClick = onHomeClick)
            InvoicesBottomNavItem(currentRoute = currentRoute, onInvoicesClick = onInvoicesClick)
        }
    }
}

@Composable
private fun RowScope.StatsBottomNavItem(currentRoute: String, onStatsClick: () -> Unit) = Column(
    modifier = Modifier
        .weight(1f)
        .clickable { onStatsClick() },
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center
) {
    val contentColor = if (currentRoute == BottomNavRoutes.home) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.tertiary
    }
    Icon(
        modifier = Modifier
            .padding(top = 12.dp, bottom = 4.dp)
            .size(32.dp),
        painter = painterResource(id = R.drawable.ic_piechart),
        contentDescription = "Home Icon",
        tint = contentColor
    )
    Text(text = stringResource(R.string.stats), style = MaterialTheme.typography.bodyMedium, color = contentColor)
}

@Composable
private fun RowScope.InvoicesBottomNavItem(currentRoute: String, onInvoicesClick: () -> Unit) = Column(
    modifier = Modifier
        .weight(1f)
        .clickable { onInvoicesClick() },
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center
) {
    val contentColor = if (currentRoute == BottomNavRoutes.invoices) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.tertiary
    }
    Icon(
        modifier = Modifier
            .padding(top = 12.dp)
            .size(32.dp),
        painter = painterResource(id = R.drawable.ic_invoice),
        contentDescription = "Invoices Icon",
        tint = contentColor
    )

    Text(text = stringResource(R.string.invoices), style = MaterialTheme.typography.bodyMedium, color = contentColor)
}

@Composable
private fun TopBar(onSettingsClick: () -> Unit) = Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
    Icon(
        modifier = Modifier
            .clickable { onSettingsClick() }
            .padding(12.dp)
            .size(32.dp),
        imageVector = Icons.Filled.Settings,
        contentDescription = "Settings Icon",
        tint = MaterialTheme.colorScheme.primary
    )
}

@Composable
private fun MainFloatingActionButton(onClick: () -> Unit) = LargeFloatingActionButton(
    onClick = onClick,
    shape = CircleShape,
    modifier = Modifier.offset(y = 60.dp),
    containerColor = MaterialTheme.colorScheme.onSecondary
) {
    Icon(
        imageVector = Icons.Filled.Add,
        contentDescription = "Add Button",
        modifier = Modifier.size(50.dp),
        tint = MaterialTheme.colorScheme.primary
    )
}

@Composable
private fun BottomSheets(
    shouldShowBottomSheet: Boolean,
    uiEvent: (MainUiEvent) -> Unit
) {
    if (shouldShowBottomSheet) {
        AppModalBottomSheet(onDismiss = { uiEvent(CloseBottomSheet) }) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
            ) {
                Button(modifier = Modifier.fillMaxWidth(), onClick = { uiEvent(CreateInvoicePressed) }) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            modifier = Modifier.size(32.dp),
                            painter = painterResource(id = R.drawable.ic_new_invoice),
                            contentDescription = "New invoice button"
                        )
                        SpacerLarge(HORIZONTAL)
                        Text(text = stringResource(R.string.add_invoice))
                    }
                }
                SpacerLarge()
                Button(modifier = Modifier.fillMaxWidth(), onClick = { uiEvent(AddClientPressed) }) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            modifier = Modifier.size(32.dp),
                            painter = painterResource(id = R.drawable.ic_client),
                            contentDescription = "New client button"
                        )
                        SpacerLarge(HORIZONTAL)
                        Text(text = stringResource(R.string.add_client))
                    }
                }
                SpacerLarge()
            }
        }
    }
}

private val navOptions = navOptions {
    popUpTo(BottomNavRoutes.home) { saveState = true }
    launchSingleTop = true
    restoreState = true
}

private const val TRANSITION_TIME = 500

@ScreenLightDarkPreview
@Composable
private fun MainScreenPreview() = ScreenPreview {
    MainScreen(MainUiState())
}
