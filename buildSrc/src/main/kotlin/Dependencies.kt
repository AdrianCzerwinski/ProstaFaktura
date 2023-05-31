object Versions {
    const val activityCompose = "1.7.1"
    const val androidxTestExtJunit = "1.1.3"
    const val bom = "2023.04.01"
    const val compose = "1.4.3"
    const val composeNavigation = "2.5.3"
    const val coreKtx = "1.10.1"
    const val detekt = "1.22.0"
    const val espressoCore = "3.5.1"
    const val junit = "4.13.2"
    const val ktlint = "11.1.0"
    const val lifecycleRuntimeKtx = "2.6.1"
    const val material3 = "1.1.0"
    const val datastore = "1.0.0"
    const val hilt = "2.46.1"
    const val hiltCore = "1.0.0"
    const val mockk = "1.12.0"
    const val turbine = "0.5.2"
    const val coroutinesTest = "1.6.0"
    const val truth = "1.1.3"
    const val fonts = "1.4.3"
    const val composeLifecycle = "2.6.1"
    const val room = "2.5.1"
}

object Dependencies {
    const val activityCompose = "androidx.activity:activity-compose:${Versions.activityCompose}"
    const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
    const val lifecycleRuntimeKtx = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycleRuntimeKtx}"

    object Compose {
        const val bom = "androidx.compose:compose-bom:${Versions.bom}"
        const val material3 = "androidx.compose.material3:material3:${Versions.material3}"
        const val ui = "androidx.compose.ui:ui:${Versions.compose}"
        const val uiGraphics = "androidx.compose.ui:ui-graphics:${Versions.compose}"
        const val uiToolingPreview = "androidx.compose.ui:ui-tooling-preview:${Versions.compose}"
        const val uiTestJUnit4 = "androidx.compose.ui:ui-test-junit4:${Versions.compose}"
        const val uiTooling = "androidx.compose.ui:ui-tooling:${Versions.compose}"
        const val uiTestManifest = "androidx.compose.ui:ui-test-manifest:${Versions.compose}"
        const val navigation = "androidx.navigation:navigation-compose:${Versions.composeNavigation}"
        const val runtime = "androidx.compose.runtime:runtime:${Versions.compose}"
        const val lifeCycle = "androidx.lifecycle:lifecycle-runtime-compose:${Versions.composeLifecycle}"
    }

    object Test {
        const val junit = "junit:junit:${Versions.junit}"
        const val mockk = "io.mockk:mockk:${Versions.mockk}"
        const val turbine = "app.cash.turbine:turbine:${Versions.turbine}"
        const val coroutinesTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutinesTest}"
        const val truth = "com.google.truth:truth:${Versions.truth
        }"

        object AndroidXTest {
            const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espressoCore}"
            const val extJunit = "androidx.test.ext:junit:${Versions.androidxTestExtJunit}"
        }
    }

    object CleanCode {
        const val detekt = "io.gitlab.arturbosch.detekt:detekt-gradle-plugin:${Versions.detekt}"
        const val ktlint = "org.jlleitschuh.gradle:ktlint-gradle:${Versions.ktlint}"
    }

    object Database {
        const val datastore = "androidx.datastore:datastore-preferences:${Versions.datastore}"

        object Room {
            const val room = "androidx.room:room-runtime:${Versions.room}"
            const val roomCompiler = "androidx.room:room-compiler:${Versions.room}"
            const val roomCoroutines = "androidx.room:room-ktx:${Versions.room}"
            const val roomTest = "androidx.room:room-testing:${Versions.room}"
            const val roomPaging = "androidx.room:room-paging:${Versions.room}"
        }
    }

    object DI {
        const val hilt = "com.google.dagger:hilt-android:${Versions.hilt}"
        const val hiltCompiler = "com.google.dagger:hilt-compiler:${Versions.hilt}"
        const val hiltCoreCompiler = "androidx.hilt:hilt-compiler:${Versions.hiltCore}"
        const val hiltComposeNavigation = "androidx.hilt:hilt-navigation-compose:${Versions.hiltCore}"
        const val hiltPlugin = "com.google.dagger:hilt-android-gradle-plugin:${Versions.hilt}"
    }

    object Google {
        const val fonts = "androidx.compose.ui:ui-text-google-fonts:${Versions.fonts}"
    }
}

object Plugins {
    const val detekt = "io.gitlab.arturbosch.detekt"
    const val ktlint = "org.jlleitschuh.gradle.ktlint"
    const val maven = "https://plugins.gradle.org/m2/"
}
