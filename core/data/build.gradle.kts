import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
plugins {
    id("ddanddan.android.library")
    kotlin("plugin.serialization") version libs.versions.kotlinVersion
    alias(libs.plugins.androidKotlin)
}

android {
    flavorDimensions.add("environment")

    productFlavors {
        create("dev") {
            dimension = "environment"

            buildConfigField("String", "BASE_URL", gradleLocalProperties(rootDir).getProperty("dev.base.url"))
        }
        create("prod") {
            dimension = "environment"

            buildConfigField("String", "BASE_URL", gradleLocalProperties(rootDir).getProperty("base.url"))
        }
    }

    buildFeatures {
        buildConfig = true
    }

    namespace = "com.ddanddan.data"
}

dependencies {
    implementation(project(":core:domain"))

    implementation(libs.androidx.coreKtx)
    implementation(libs.androidx.pagingRuntime)
    implementation(libs.androidx.security)
    implementation(libs.billing)

    implementation(libs.gson)
    implementation(libs.okhttp.bom)
    implementation(libs.bundles.okhttp)
    implementation(libs.bundles.retrofit)
    implementation(libs.timber)
    implementation(libs.androidx.appCompat)
    implementation(libs.androidx.constraintLayout)
    implementation(libs.kakaoLogin)
}
