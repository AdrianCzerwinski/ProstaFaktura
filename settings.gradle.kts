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
include(":core:ui")
include(":core:common")
include(":data:user")
include(":domain:user")
