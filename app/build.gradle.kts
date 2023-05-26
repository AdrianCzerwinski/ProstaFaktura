import io.gitlab.arturbosch.detekt.Detekt

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("io.gitlab.arturbosch.detekt") version "1.22.0"
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
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    kotlinOptions {
        jvmTarget = AppData.jvmTarget
    }

    tasks.withType<Detekt>().configureEach {
        jvmTarget = AppData.jvmTarget
    }
}

dependencies {
    implementation(Dependencies.coreKtx)
    implementation(Dependencies.lifecycleRuntimeKtx)
    implementation(Dependencies.activityCompose)

    implementation(platform(Dependencies.Compose.bom))
    implementation(Dependencies.Compose.ui)
    implementation(Dependencies.Compose.uiGraphics)
    implementation(Dependencies.Compose.uiToolingPreview)
    implementation(Dependencies.Compose.material3)

    testImplementation(Dependencies.Test.junit)

    androidTestImplementation(Dependencies.Test.AndroidXTest.extJunit)
    androidTestImplementation(Dependencies.Test.AndroidXTest.espressoCore)
    androidTestImplementation(platform(Dependencies.Compose.bom))
    androidTestImplementation(Dependencies.Compose.uiTestJUnit4)

    debugImplementation(Dependencies.Compose.uiTooling)
    debugImplementation(Dependencies.Compose.uiTestManifest)
}
