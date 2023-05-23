object Versions {
    const val ktlint = "0.45.2"
    const val coreKtx = "1.10.1"
    const val lifecycleRuntimeKtx = "2.6.1"
    const val activityCompose = "1.7.1"
    const val compose = "1.4.3"
    const val junit = "4.13.2"
    const val androidxTestExtJunit = "1.1.3"
    const val espressoCore = "3.5.1"
    const val bom = "2023.04.01"
    const val material3 = "1.1.0"
}

object Dependencies {
    const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
    const val lifecycleRuntimeKtx = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycleRuntimeKtx}"
    const val activityCompose = "androidx.activity:activity-compose:${Versions.activityCompose}"

    object Compose {
        const val bom = "androidx.compose:compose-bom:${Versions.bom}"
        const val ui = "androidx.compose.ui:ui:${Versions.compose}"
        const val uiGraphics = "androidx.compose.ui:ui-graphics:${Versions.compose}"
        const val uiToolingPreview = "androidx.compose.ui:ui-tooling-preview:${Versions.compose}"
        const val material3 = "androidx.compose.material3:material3:${Versions.material3}"
        const val uiTestJUnit4 = "androidx.compose.ui:ui-test-junit4:${Versions.compose}"
        const val uiTooling = "androidx.compose.ui:ui-tooling:${Versions.compose}"
        const val uiTestManifest = "androidx.compose.ui:ui-test-manifest:${Versions.compose}"
    }

    object Test {
        const val junit = "junit:junit:${Versions.junit}"

        object AndroidXTest {
            const val extJunit = "androidx.test.ext:junit:${Versions.androidxTestExtJunit}"
            const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espressoCore}"
        }
    }
}
