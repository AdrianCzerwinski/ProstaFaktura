import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    kotlin("kapt")
}

android {
    namespace = "pl.adrianczerwinski.prostafaktura.domain.client"

    composeOptions {
        kotlinCompilerExtensionVersion = AppData.kotlinCompilerExtensionVersion
    }
    kotlinOptions {
        jvmTarget = AppData.jvmTarget
    }

    tasks.withType<io.gitlab.arturbosch.detekt.Detekt>().configureEach {
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
    implementation(project(":data:client"))
    implementation(project(":core:common"))

    implementation(Dependencies.coreKtx)
    implementation(Dependencies.lifecycleRuntimeKtx)

    implementation(Dependencies.DI.hilt)
    kapt(Dependencies.DI.hiltCompiler)
    kapt(Dependencies.DI.hiltCoreCompiler)

    implementation(Dependencies.Compose.runtime)

    implementation(Dependencies.Database.datastore)
}

kapt {
    correctErrorTypes = true
}

hilt {
    enableAggregatingTask = true
}
