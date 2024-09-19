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
}
