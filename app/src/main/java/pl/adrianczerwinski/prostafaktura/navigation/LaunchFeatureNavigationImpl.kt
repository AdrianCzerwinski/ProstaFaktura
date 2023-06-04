package pl.adrianczerwinski.prostafaktura.navigation

import androidx.navigation.NavController
import pl.adrianczerwinski.launch.LaunchFeatureNavigation

class LaunchFeatureNavigationImpl(
    private val navController: NavController
) : LaunchFeatureNavigation {
    override fun openOnboarding() = navController.navigate(Destinations.Onboarding.ROUTE) {
        popUpTo(Destinations.Launch.ROUTE) { inclusive = true }
    }

    override fun openMainScreen() = navController.navigate(Destinations.MainDestination.ROUTE) {
        popUpTo(Destinations.Launch.ROUTE) { inclusive = true }
    }
}
