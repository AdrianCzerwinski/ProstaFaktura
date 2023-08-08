package pl.adrianczerwinski.prostafaktura.navigation

import androidx.navigation.NavController
import pl.adrianczerwinski.settings.SettingsFeatureNavigation

class SettingsNavigationImpl(
    private val navController: NavController
) : SettingsFeatureNavigation {
    override fun openClientsList() = navController.navigate(Destinations.SettingsDestination.CLIENTS_LIST)
    override fun openUserEdit() {
        // TODO
    }

    override fun navigateUp() {
        navController.navigateUp()
    }

    override fun openClient(clientId: Int) {
        // TODO
    }

    override fun openAddClient() = navController.navigate(Destinations.ClientDestination.ROUTE)
}
