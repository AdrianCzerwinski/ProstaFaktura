plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    kotlin("kapt")
}

android {
    namespace = "pl.adrianczerwinski.prostafaktura.core.common"

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
    tasks.withType<io.gitlab.arturbosch.detekt.Detekt>().configureEach {
        jvmTarget = AppData.jvmTarget
    }
}

dependencies {
    implementation(Dependencies.coreKtx)
    implementation(Dependencies.lifecycleRuntimeKtx)
    implementation(Dependencies.activityCompose)

    implementation(Dependencies.DI.hilt)
    kapt(Dependencies.DI.hiltCompiler)
    kapt(Dependencies.DI.hiltCoreCompiler)
}

kapt {
    correctErrorTypes = true
}

hilt {
    enableAggregatingTask = true
}
