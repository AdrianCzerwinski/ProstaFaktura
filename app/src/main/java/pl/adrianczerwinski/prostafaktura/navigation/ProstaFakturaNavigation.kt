package pl.adrianczerwinski.prostafaktura.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import pl.adrianczerwinski.launch.Launch

@Composable
fun ProstaFakturaNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "launch") {
        composable("launch") {
            Launch()
        }

        navigation(
            startDestination = "onboarding_welcome",
            route = "onboarding"
        ) {
            composable("onboarding_welcome") {}
            composable("onboarding_user_info") {}
            composable("onboarding_payment_info") {}
        }
    }
}
