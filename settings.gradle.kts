pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Prosta Faktura"
include(":app")
include(":features:launch")
include(":features:onboarding")
include(":features:main")
include(":features:addclient")
include(":features:settings")
include(":core:ui")
include(":core:common")
include(":data:user")
include(":data:client")
include(":domain:user")
include(":domain:validation")
include(":domain:client")
