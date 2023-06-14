package pl.adrianczerwinski.prostafaktura.navigation

import androidx.navigation.NavController
import pl.adrianczerwinski.addclient.AddClientNavigation
import pl.adrianczerwinski.launch.LaunchFeatureNavigation

class AddClientNavigationImpl(
    private val navController: NavController
) : AddClientNavigation {
    override fun closeAddClientFlow() {
        navController.popBackStack(Destinations.Client.ROUTE, true)
    }
}
