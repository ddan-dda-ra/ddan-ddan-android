plugins {
    `kotlin-dsl`
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    compileOnly(libs.android.build)
    compileOnly(libs.kotlin.gradle)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "ddanddan.android.application"
            implementationClass = "plugins.AndroidApplicationPlugin"
        }
        register("androidLibrary") {
            id = "ddanddan.android.library"
            implementationClass = "plugins.AndroidLibraryPlugin"
        }
        register("androidHilt") {
            id = "ddanddan.android.androidHilt"
            implementationClass = "plugins.AndroidHiltPlugin"
        }
        register("androidKotlin") {
            id = "ddanddan.android.kotlin"
            implementationClass = "plugins.AndroidKotlinPlugin"
        }
    }
}
