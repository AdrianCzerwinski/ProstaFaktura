plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("io.gitlab.arturbosch.detekt") version "1.22.0"
    id("com.google.dagger.hilt.android")
    kotlin("kapt")
}

detekt {
    toolVersion = Versions.detekt
    config = files("$rootDir/gradle/detekt-config.yml")
    parallel = true

    buildUponDefaultConfig = true
}

android {
    namespace = "pl.adrianczerwinski.prostafaktura"

    composeOptions {
        kotlinCompilerExtensionVersion = AppData.kotlinCompilerExtensionVersion
    }

    defaultConfig.applicationId = AppData.applicationId
}

dependencies {

    implementation(project(":features:launch"))

    implementation(Dependencies.coreKtx)
    implementation(Dependencies.lifecycleRuntimeKtx)
    implementation(Dependencies.activityCompose)

    implementation(platform(Dependencies.Compose.bom))
    implementation(Dependencies.Compose.ui)
    implementation(Dependencies.Compose.uiGraphics)
    implementation(Dependencies.Compose.uiToolingPreview)
    implementation(Dependencies.Compose.material3)
    implementation(Dependencies.Compose.runtime)

    implementation(Dependencies.Compose.navigation)

    implementation(Dependencies.DI.hilt)
    kapt(Dependencies.DI.hiltCompiler)
    kapt(Dependencies.DI.hiltCoreCompiler)

    testImplementation(Dependencies.Test.junit)

    androidTestImplementation(Dependencies.Test.AndroidXTest.extJunit)
    androidTestImplementation(Dependencies.Test.AndroidXTest.espressoCore)
    androidTestImplementation(platform(Dependencies.Compose.bom))
    androidTestImplementation(Dependencies.Compose.uiTestJUnit4)

    debugImplementation(Dependencies.Compose.uiTooling)
    debugImplementation(Dependencies.Compose.uiTestManifest)
}

kapt {
    correctErrorTypes = true
}
