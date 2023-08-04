package pl.adrianczerwinski.prostafaktura.navigation

import androidx.navigation.NavController
import pl.adrianczerwinski.addclient.AddClientNavigation

class AddClientNavigationImpl(
    private val navController: NavController
) : AddClientNavigation {
    override fun closeAddClientFlow() {
        navController.popBackStack(Destinations.Client.ROUTE, true)
    }
}
