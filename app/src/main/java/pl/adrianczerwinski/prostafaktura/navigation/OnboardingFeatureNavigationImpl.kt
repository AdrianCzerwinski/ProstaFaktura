package pl.adrianczerwinski.prostafaktura.navigation

import androidx.navigation.NavController
import pl.adrianczerwinski.onboarding.OnboardingFeatureNavigation

class OnboardingFeatureNavigationImpl(
    private val navController: NavController
) : OnboardingFeatureNavigation {
    override fun openUserInfo() = navController.navigate(Destinations.Onboarding.SIGN_IN)

    override fun openMainScreen() = navController.navigate(Destinations.Main.ROUTE) {
        popUpTo(Destinations.Onboarding.ROUTE) { inclusive = true }
    }
}
