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
include(":core")
include(":data")
include(":domain")
include(":features")
include(":buildValues")
include(":core:buildsrc")
