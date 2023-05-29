import io.gitlab.arturbosch.detekt.Detekt
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    kotlin("kapt")
}

android {
    namespace = "pl.adrianczerwinski.prostafaktura.data.user"

    composeOptions {
        kotlinCompilerExtensionVersion = AppData.kotlinCompilerExtensionVersion
    }
    kotlinOptions {
        jvmTarget = AppData.jvmTarget
    }

    tasks.withType<Detekt>().configureEach {
        jvmTarget = AppData.jvmTarget
    }

    java {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    tasks.withType<KotlinCompile>().configureEach {
        kotlinOptions {
            jvmTarget = AppData.jvmTarget
        }
    }
}

dependencies {
    implementation(Dependencies.coreKtx)
    implementation(Dependencies.lifecycleRuntimeKtx)

    implementation(Dependencies.DI.hilt)
    kapt(Dependencies.DI.hiltCompiler)
    kapt(Dependencies.DI.hiltCoreCompiler)

    implementation(Dependencies.Database.datastore)

    implementation(Dependencies.Compose.runtime)
}

kapt {
    correctErrorTypes = true
}

hilt {
    enableAggregatingTask = true
}
