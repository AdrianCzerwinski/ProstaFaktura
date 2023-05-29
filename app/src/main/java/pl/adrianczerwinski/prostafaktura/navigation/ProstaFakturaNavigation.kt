package pl.adrianczerwinski.prostafaktura.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import pl.adrianczerwinski.launch.Launch
import pl.adrianczerwinski.launch.LaunchFeatureNavigation
import pl.adrianczerwinski.prostafaktura.navigation.Destinations.Launch
import pl.adrianczerwinski.prostafaktura.navigation.Destinations.Onboarding

@Composable
fun ProstaFakturaNavigation(navController: NavHostController) {
    val launchFeatureNavigation: LaunchFeatureNavigation = LaunchFeatureNavigationImpl(navController)

    NavHost(navController = navController, startDestination = Launch.ROUTE) {
        composable(Launch.ROUTE) {
            Launch(launchFeatureNavigation)
        }

        navigation(
            startDestination = Onboarding.ONBOARDING_WELCOME,
            route = Onboarding.ROUTE
        ) {
            composable(Onboarding.ONBOARDING_WELCOME) {}
            composable(Onboarding.ONBOARDING_USER_INFO) {}
            composable(Onboarding.ONBOARDING_PAYMENT_INFO) {}
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
        const val ONBOARDING_PAYMENT_INFO = "onboarding_payment_info"
    }

    object Main {
        const val ROUTE = "main"

        const val MAIN_HOME = "main_home"
        const val MAIN_INFO = "main_info"
    }
}
