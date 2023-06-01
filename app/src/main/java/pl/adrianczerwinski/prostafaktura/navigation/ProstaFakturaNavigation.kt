package pl.adrianczerwinski.prostafaktura.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import pl.adrianczerwinski.launch.Launch
import pl.adrianczerwinski.launch.LaunchFeatureNavigation
import pl.adrianczerwinski.onboarding.OnboardingFeatureNavigation
import pl.adrianczerwinski.onboarding.signin.SignIn
import pl.adrianczerwinski.onboarding.welcome.Welcome
import pl.adrianczerwinski.prostafaktura.navigation.Destinations.Launch
import pl.adrianczerwinski.prostafaktura.navigation.Destinations.Main
import pl.adrianczerwinski.prostafaktura.navigation.Destinations.Onboarding

@Composable
fun ProstaFakturaNavigation(navController: NavHostController) {
    val launchFeatureNavigation: LaunchFeatureNavigation = LaunchFeatureNavigationImpl(navController)
    val onboardingFeatureNavigationImpl: OnboardingFeatureNavigation = OnboardingFeatureNavigationImpl(navController)

    Surface(modifier = Modifier.fillMaxSize().systemBarsPadding().navigationBarsPadding()) {
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
                composable(Onboarding.ONBOARDING_WELCOME) { Welcome(onboardingFeatureNavigationImpl) }
                composable(Onboarding.SIGN_IN) { SignIn(onboardingFeatureNavigationImpl) }
            }

            navigation(
                startDestination = Main.MAIN_HOME,
                route = Main.ROUTE
            ) {
                composable(Main.MAIN_HOME) {}
                composable(Main.MAIN_INFO) {}
            }
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
        const val SIGN_IN = "onboarding_user_info"
    }

    object Main {
        const val ROUTE = "main"

        const val MAIN_HOME = "main_home"
        const val MAIN_INFO = "main_info"
    }
}
