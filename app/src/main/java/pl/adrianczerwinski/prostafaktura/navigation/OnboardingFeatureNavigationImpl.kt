package pl.adrianczerwinski.prostafaktura.navigation

import androidx.navigation.NavController
import pl.adrianczerwinski.onboarding.OnboardingFeatureNavigation

class OnboardingFeatureNavigationImpl(
    private val navController: NavController
) : OnboardingFeatureNavigation {
    override fun openUserInfo() = navController.navigate(Destinations.Onboarding.ONBOARDING_USER_INFO)

    override fun openAccountInfo() = navController.navigate(Destinations.Onboarding.ONBOARDING_ACCOUNT_INFO)

    override fun openMainScreen() = navController.navigate(Destinations.Main.ROUTE) {
        popUpTo(Destinations.Onboarding.ROUTE) { inclusive = true }
    }
}
