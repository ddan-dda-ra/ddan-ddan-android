plugins {
    id("ddanddan.android.application")
    id("ddanddan.android.androidHilt")
    id("ddanddan.android.kotlin")
    alias(libs.plugins.androidKotlin)
}
dependencies {
    implementation(project(":core:ui"))
    implementation(project(":core:data"))
    implementation(project(":core:domain"))
    
    // Wear OS Integration
    implementation(libs.play.services.wearable)
    implementation(libs.kotlin.coroutines)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.7.3")

}
