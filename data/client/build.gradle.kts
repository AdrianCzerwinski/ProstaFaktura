import io.gitlab.arturbosch.detekt.Detekt
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    id("kotlin-parcelize")
    kotlin("kapt")
}

android {
    namespace = "pl.adrianczerwinski.prostafaktura.data.client"

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

    defaultConfig {
        javaCompileOptions {
            annotationProcessorOptions {
                arguments += mapOf(
                    "room.schemaLocation" to "$projectDir/schemas",
                    "room.incremental" to "true",
                    "room.expandProjection" to "true"
                )
            }
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
    implementation(Dependencies.Database.Room.room)
    annotationProcessor(Dependencies.Database.Room.roomCompiler)
    implementation(Dependencies.Database.Room.roomCoroutines)
    kapt(Dependencies.Database.Room.roomCompiler)
    implementation(Dependencies.gson)

    implementation(Dependencies.Compose.runtime)
}

kapt {
    correctErrorTypes = true
}

hilt {
    enableAggregatingTask = true
}
