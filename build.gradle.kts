import com.android.build.gradle.AppExtension
import com.android.build.gradle.AppPlugin
import com.android.build.gradle.BaseExtension
import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.LibraryPlugin
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("com.android.application") version "8.1.0-rc01" apply false
    id("org.jetbrains.kotlin.android") version "1.8.10" apply false
    id("org.jetbrains.kotlin.jvm") version "1.8.10" apply false
    id("com.android.library") version "8.1.0-rc01" apply false
    id("io.gitlab.arturbosch.detekt") version "1.22.0"
    id("com.google.dagger.hilt.android") version Versions.hilt apply false
    `kotlin-dsl`
}

allprojects {
    project.plugins.applyBaseConfig(project)
    apply(plugin = Plugins.ktlint)
    apply(plugin = Plugins.detekt)

    detekt {
        toolVersion = Versions.detekt
        config = files("$rootDir/gradle/detekt-config.yml")
        parallel = true

        buildUponDefaultConfig = true
    }

    tasks.withType<io.gitlab.arturbosch.detekt.Detekt>().configureEach {
        jvmTarget = AppData.jvmTarget
    }

    buildscript {
        repositories {
            maven(Plugins.maven)
        }
        dependencies {
            classpath(Dependencies.CleanCode.ktlint)
            classpath(Dependencies.DI.hiltPlugin)
        }
    }

    tasks.withType<KotlinCompile>().all {
        kotlinOptions {
            jvmTarget = AppData.jvmTarget
        }
    }
}

buildscript {
    repositories {
        maven(Plugins.maven)
    }
    dependencies {
        classpath(Dependencies.CleanCode.ktlint)
        classpath(Dependencies.DI.hiltPlugin)
    }
}

fun BaseExtension.baseConfig() {
    compileSdkVersion(AppData.compileSdk)

    defaultConfig.apply {
        minSdk = AppData.minSdk
        targetSdk = AppData.targetSdk
        versionCode = AppData.versionCode
        versionName = AppData.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false
            isDebuggable = true
        }
        getByName("release") {
            isMinifyEnabled = true
            isDebuggable = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildFeatures.compose = true
}

fun PluginContainer.applyBaseConfig(project: Project) {
    whenPluginAdded {
        when (this) {
            is AppPlugin -> {
                project.extensions
                    .getByType<AppExtension>()
                    .apply {
                        baseConfig()
                    }
            }

            is LibraryPlugin -> {
                project.extensions
                    .getByType<LibraryExtension>()
                    .apply {
                        baseConfig()
                    }
            }
        }
    }
}
