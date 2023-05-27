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
    }

    object Test {
        const val junit = "junit:junit:${Versions.junit}"

        object AndroidXTest {
            const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espressoCore}"
            const val extJunit = "androidx.test.ext:junit:${Versions.androidxTestExtJunit}"
        }
    }

    object CleanCode {

        const val detekt = "io.gitlab.arturbosch.detekt:detekt-gradle-plugin:${Versions.detekt}"
        const val ktlint = "org.jlleitschuh.gradle:ktlint-gradle:${Versions.ktlint}"
    }
}

object Plugins {
    const val detekt = "io.gitlab.arturbosch.detekt"
    const val ktlint = "org.jlleitschuh.gradle.ktlint"
    const val maven = "https://plugins.gradle.org/m2/"
}
