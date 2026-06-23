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
            buildConfigField("String", "BASE_URL", gradleLocalProperties(rootDir, providers).getProperty("dev.base.url"))
        }
        create("prod") {
            dimension = "environment"
            buildConfigField("String", "BASE_URL", gradleLocalProperties(rootDir, providers).getProperty("base.url"))
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
//    implementation(libs.billing)

    implementation(libs.gson)
    implementation(platform(libs.okhttp.bom))
    implementation(libs.bundles.okhttp)
    implementation(libs.bundles.retrofit)
    implementation(libs.timber)
    implementation(libs.androidx.appCompat)
    implementation(libs.androidx.constraintLayout)
    implementation(libs.kakaoLogin)
    implementation(libs.androidx.datastore.core.android)
    implementation(libs.androidx.datastore.prefenrences.android)
    implementation(libs.kotlin.coroutines)
}
