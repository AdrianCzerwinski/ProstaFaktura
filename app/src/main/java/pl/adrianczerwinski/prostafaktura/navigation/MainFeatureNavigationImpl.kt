package pl.adrianczerwinski.prostafaktura.navigation

import androidx.navigation.NavController
import pl.adrianczerwinski.main.MainFeatureNavigation

class MainFeatureNavigationImpl(private val navController: NavController) : MainFeatureNavigation {
    override fun openCreateInvoice() = navController.navigate(Destinations.Invoice.ROUTE)

    override fun openAddClient() = navController.navigate(Destinations.Client.ROUTE)

    override fun openSettings() = navController.navigate(Destinations.Settings.ROUTE)
}
