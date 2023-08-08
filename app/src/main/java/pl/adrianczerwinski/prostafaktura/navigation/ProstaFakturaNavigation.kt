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
import pl.adrianczerwinski.addclient.AddClient
import pl.adrianczerwinski.addclient.AddClientNavigation
import pl.adrianczerwinski.launch.Launch
import pl.adrianczerwinski.launch.LaunchFeatureNavigation
import pl.adrianczerwinski.main.Main
import pl.adrianczerwinski.main.MainFeatureNavigation
import pl.adrianczerwinski.onboarding.OnboardingFeatureNavigation
import pl.adrianczerwinski.onboarding.signin.SignIn
import pl.adrianczerwinski.onboarding.welcome.Welcome
import pl.adrianczerwinski.prostafaktura.navigation.Destinations.ClientDestination
import pl.adrianczerwinski.prostafaktura.navigation.Destinations.InvoiceDestination
import pl.adrianczerwinski.prostafaktura.navigation.Destinations.Launch
import pl.adrianczerwinski.prostafaktura.navigation.Destinations.MainDestination
import pl.adrianczerwinski.prostafaktura.navigation.Destinations.Onboarding
import pl.adrianczerwinski.prostafaktura.navigation.Destinations.SettingsDestination
import pl.adrianczerwinski.settings.Settings
import pl.adrianczerwinski.settings.SettingsFeatureNavigation
import pl.adrianczerwinski.settings.clients.Clients

@Composable
fun ProstaFakturaNavigation(navController: NavHostController) {
    val launchFeatureNavigation: LaunchFeatureNavigation = LaunchFeatureNavigationImpl(navController)
    val onboardingFeatureNavigationImpl: OnboardingFeatureNavigation = OnboardingFeatureNavigationImpl(navController)
    val mainFeatureNavigationImpl: MainFeatureNavigation = MainFeatureNavigationImpl(navController)
    val addClientFeatureNavigationImpl: AddClientNavigation = AddClientNavigationImpl(navController)
    val settingsNavigation: SettingsFeatureNavigation = SettingsNavigationImpl(navController)

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .navigationBarsPadding()
    ) {
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
                startDestination = MainDestination.MAIN_HOME,
                route = MainDestination.ROUTE
            ) {
                composable(MainDestination.MAIN_HOME) {
                    Main(navigation = mainFeatureNavigationImpl)
                }
            }

            navigation(
                startDestination = SettingsDestination.SETTINGS_MAIN,
                route = SettingsDestination.ROUTE
            ) {
                composable(SettingsDestination.SETTINGS_MAIN) { Settings(navigation = settingsNavigation) }
                composable(SettingsDestination.CLIENTS_LIST) { Clients(navigation = settingsNavigation) }
            }

            navigation(
                startDestination = InvoiceDestination.INVOICE_CREATE,
                route = InvoiceDestination.ROUTE
            ) {
                composable(InvoiceDestination.INVOICE_CREATE) {}
            }

            navigation(
                startDestination = ClientDestination.CLIENT_ADD,
                route = ClientDestination.ROUTE
            ) {
                composable(ClientDestination.CLIENT_ADD) { AddClient(addClientFeatureNavigationImpl) }
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

    object MainDestination {
        const val ROUTE = "main"
        const val MAIN_HOME = "main_home"
    }

    object SettingsDestination {
        const val ROUTE = "settings"
        const val SETTINGS_MAIN = "settings_main"
        const val CLIENTS_LIST = "clients_list"
    }

    object InvoiceDestination {
        const val ROUTE = "invoice"
        const val INVOICE_CREATE = "invoice_CREATE"
    }

    object ClientDestination {
        const val ROUTE = "client"
        const val CLIENT_ADD = "client_add"
    }
}
