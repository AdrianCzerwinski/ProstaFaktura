package pl.adrianczerwinski.prostafaktura.navigation

import androidx.navigation.NavController
import pl.adrianczerwinski.main.MainFeatureNavigation

class MainFeatureNavigationImpl(private val navController: NavController) : MainFeatureNavigation {
    override fun openCreateInvoice() = navController.navigate(Destinations.InvoiceDestination.ROUTE)

    override fun openAddClient() = navController.navigate(Destinations.ClientDestination.ROUTE)

    override fun openSettings() = navController.navigate(Destinations.SettingsDestination.ROUTE)
}
