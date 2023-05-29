package pl.adrianczerwinski.prostafaktura.navigation

import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import pl.adrianczerwinski.launch.Launch
import pl.adrianczerwinski.launch.LaunchFeatureNavigation
import pl.adrianczerwinski.onboarding.Onboarding
import pl.adrianczerwinski.onboarding.OnboardingFeatureNavigation
import pl.adrianczerwinski.prostafaktura.navigation.Destinations.Launch
import pl.adrianczerwinski.prostafaktura.navigation.Destinations.Onboarding

@Composable
fun ProstaFakturaNavigation(navController: NavHostController) {
    val launchFeatureNavigation: LaunchFeatureNavigation = LaunchFeatureNavigationImpl(navController)
    val onboardingFeatureNavigationImpl: OnboardingFeatureNavigation = OnboardingFeatureNavigationImpl(navController)

    NavHost(
        modifier = Modifier.background(MaterialTheme.colorScheme.background),
        navController = navController,
        startDestination = Launch.ROUTE
    ) {
        composable(Launch.ROUTE) {
            Launch(launchFeatureNavigation)
        }

        navigation(
            startDestination = Onboarding.ONBOARDING_WELCOME,
            route = Onboarding.ROUTE
        ) {
            composable(Onboarding.ONBOARDING_WELCOME) { Onboarding(onboardingFeatureNavigationImpl) }
            composable(Onboarding.ONBOARDING_USER_INFO) {}
            composable(Onboarding.ONBOARDING_ACCOUNT_INFO) {}
        }
    }
}

object Destinations {
    object Launch {
        const val ROUTE = "launch"
    }

    object Onboarding {
        const val ROUTE = "onboarding"

        const val ONBOARDING_WELCOME = "onboarding_welcome"
        const val ONBOARDING_USER_INFO = "onboarding_user_info"
        const val ONBOARDING_ACCOUNT_INFO = "onboarding_payment_info"
    }

    object Main {
        const val ROUTE = "main"

        const val MAIN_HOME = "main_home"
        const val MAIN_INFO = "main_info"
    }
}
