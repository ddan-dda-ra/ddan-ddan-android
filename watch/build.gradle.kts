plugins {
    id("ddanddan.android.application")
    id("ddanddan.android.androidHilt")
    id("ddanddan.android.kotlin")
    alias(libs.plugins.androidKotlin)
}

android {
    namespace = "com.ddanddan.watch"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.ddanddan.watch"
        minSdk = 30
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(libs.play.services.wearable)
    implementation(libs.bundles.watch)
    implementation(libs.bundles.tile)

    implementation(project(":core:ui"))
    implementation(project(":core:data"))
    implementation(project(":core:domain"))
}