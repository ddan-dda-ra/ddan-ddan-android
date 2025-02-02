import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.androidKotlin)
    alias(libs.plugins.androidHilt)
    alias(libs.plugins.serialization)
    kotlin("kapt")
}
android {
    namespace = "com.ddanddan.ddanddan"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.ddanddan.ddanddan"
        minSdk = 30
        targetSdk = 34
        versionCode = 4
        versionName = "1.0"

        buildConfigField(
            "String",
            "BASE_URL",
            gradleLocalProperties(rootDir).getProperty("base.url"),
        )
    }

    signingConfigs {
        getByName("debug") {
            storeFile =
                file("../app/ddanddan_debug.keystore")
            storePassword = gradleLocalProperties(rootDir).getProperty("storePassword")
            keyAlias = gradleLocalProperties(rootDir).getProperty("keyAlias")
            keyPassword = gradleLocalProperties(rootDir).getProperty("keyPassword")
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.6"
    }

    buildFeatures {
        buildConfig = true
        compose = true
    }

    hilt {
        enableAggregatingTask = false
    }
}

dependencies {
    implementation(project(":core:ui"))

    implementation(libs.materialDesign)
    implementation(libs.kotlin.serialization.json)
    implementation(libs.play.services.wearable)
    implementation(libs.androidx.wear.tooling.preview)
    androidTestImplementation(libs.jUnit)
    androidTestImplementation(libs.espresso)

    // Compose UI dependencies
    implementation(libs.bundles.compose)

    // Health Services
    implementation(libs.androidx.health.services)

    // Used to bridge between Futures and coroutines
    implementation(libs.guava)
    implementation(libs.concurrent.futures)

    // Permissions
    implementation(libs.accompanist.permissions)

    implementation(libs.hilt)
    implementation(libs.hilt.workmanager)
    kapt(libs.hiltAndroidCompiler)
    kapt(libs.hiltWorkManagerCompiler)

    implementation(libs.okhttp.bom)
    implementation(libs.bundles.okhttp)
    implementation(libs.bundles.androidx)
    implementation(libs.bundles.kotlin)
    implementation(libs.bundles.retrofit)

    implementation(libs.timber)
}
