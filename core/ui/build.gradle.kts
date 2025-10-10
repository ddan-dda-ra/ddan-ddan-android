plugins {
    id("ddanddan.android.library")
    alias(libs.plugins.androidKotlin)
}

android {
    buildFeatures {
        dataBinding = true
        viewBinding = true
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composeCompilerVersion.get()
    }

    namespace = "com.ddanddan.base"
}

dependencies {
    // Lifecycle Ktx
    implementation(libs.androidx.lifeCycleKtx)
    implementation(libs.androidx.appCompat)
    implementation(libs.androidx.constraintLayout)
    implementation(libs.bundles.compose)
    debugImplementation(libs.ui.tooling.compose)
}
