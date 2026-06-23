package plugins

import Constants
import com.android.build.api.dsl.ApplicationExtension
import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import com.ddanddan.convention.src.main.kotlin.ext.androidTestImplementation
import com.ddanddan.convention.src.main.kotlin.ext.debugImplementation
import com.ddanddan.convention.src.main.kotlin.ext.getBundle
import com.ddanddan.convention.src.main.kotlin.ext.getLibrary
import com.ddanddan.convention.src.main.kotlin.ext.getVersionCatalog
import com.ddanddan.convention.src.main.kotlin.ext.implementation
import com.ddanddan.convention.src.main.kotlin.ext.kapt
import com.ddanddan.convention.src.main.kotlin.ext.testImplementation
import ext.application.configureDefault
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidApplicationPlugin : Plugin<Project> {
    override fun apply(target: Project) =
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
                apply("androidx.navigation.safeargs")
                apply("com.google.android.gms.oss-licenses-plugin")
                apply("com.google.gms.google-services")
                apply("com.google.firebase.appdistribution")
                apply("com.google.firebase.crashlytics")
            }

            extensions.configure<ApplicationExtension> {
                namespace = Constants.packageName
                compileSdk = Constants.compileSdk

                configureAndroidCommonPlugin()
                configureDefault()

                packaging {
                    resources.excludes.add("META-INF/DEPENDENCIES")
                    resources.excludes.add("migrateToAndroidx/migration.xml")
                    jniLibs {
                        useLegacyPackaging = true
                    }
                }

                defaultConfig {
                    buildConfigField("String", "KAKAO_APP_KEY", gradleLocalProperties(rootDir, providers).getProperty("kakao.key"))
                    buildConfigField("String", "AES_KEY", gradleLocalProperties(rootDir, providers).getProperty("AES_KEY"))
                    manifestPlaceholders["KAKAO_APP_KEY"] = gradleLocalProperties(rootDir, providers).getProperty("kakaoAppKey")
                }

                signingConfigs {
                    getByName("debug") {
                        storeFile = file("ddanddan_debug.keystore")
                        storePassword = gradleLocalProperties(rootDir, providers).getProperty("storePassword")
                        keyAlias = gradleLocalProperties(rootDir, providers).getProperty("keyAlias")
                        keyPassword = gradleLocalProperties(rootDir, providers).getProperty("keyPassword")
                    }
                    create("release") {
                        storeFile = file("ddanddan_release_key")
                        storePassword = gradleLocalProperties(rootDir, providers).getProperty("releaseStorePassword")
                        keyAlias = gradleLocalProperties(rootDir, providers).getProperty("releaseKeyAlias")
                        keyPassword = gradleLocalProperties(rootDir, providers).getProperty("releaseKeyPassword")
                    }
                }

                buildFeatures {
                    buildConfig = true
                    viewBinding = true
                    dataBinding = true
                    compose = true
                }

                buildTypes {
                    release {
                        isMinifyEnabled = false
                        signingConfig = signingConfigs.getByName("release")
                        proguardFiles(
                            getDefaultProguardFile("proguard-android-optimize.txt"),
                            "proguard-rules.pro",
                        )
                    }
                }

                flavorDimensions.add("environment")

                productFlavors {
                    create("dev") {
                        dimension = "environment"
                        buildConfigField("String", "BASE_URL", gradleLocalProperties(rootDir, providers).getProperty("dev.base.url"))
                        buildConfigField("String", "CHOTTULINK_KEY", gradleLocalProperties(rootDir, providers).getProperty("chottulink.key"))
                    }
                    create("prod") {
                        dimension = "environment"
                        buildConfigField("String", "BASE_URL", gradleLocalProperties(rootDir, providers).getProperty("base.url"))
                        buildConfigField("String", "CHOTTULINK_KEY", gradleLocalProperties(rootDir, providers).getProperty("chottulink.key"))
                    }
                }

                compileOptions {
                    sourceCompatibility = JavaVersion.VERSION_17
                    targetCompatibility = JavaVersion.VERSION_17
                }

                composeOptions {
                    kotlinCompilerExtensionVersion = "1.5.15"
                }
            }

            val libs = extensions.getVersionCatalog()

            dependencies {
                implementation(libs.getBundle("androidx"))

                implementation(platform(libs.getLibrary("firebase-bom")))
                implementation(libs.getBundle("firebase"))

                debugImplementation(libs.getBundle("flipper"))

                implementation(libs.getBundle("retrofit"))

                testImplementation(libs.getLibrary("jUnit"))
                debugImplementation(libs.getLibrary("ui-tooling-compose"))
                androidTestImplementation(libs.getLibrary("androidTest"))
                androidTestImplementation(libs.getLibrary("espresso"))

                implementation(libs.getLibrary("ossLicense"))
                implementation(libs.getLibrary("gson"))

                implementation(platform(libs.getLibrary("okhttp-Bom")))
                implementation(libs.getBundle("okhttp"))

                implementation(libs.getBundle("compose"))

                implementation(libs.getBundle("kakao"))

                implementation(libs.getLibrary("orbit-core"))
                implementation(libs.getLibrary("orbit-viewmodel"))
                implementation(libs.getLibrary("orbit-compose"))
                testImplementation(libs.getLibrary("orbit-test"))

                implementation(libs.getLibrary("hilt"))
                kapt(libs.getLibrary("hiltAndroidCompiler"))
                kapt(libs.getLibrary("hiltWorkManagerCompiler"))

                implementation(libs.getBundle("appModuleLibraryEtc"))

                implementation(libs.getLibrary("play-services-location"))
                implementation(libs.getLibrary("play-services-wearable"))

                implementation(libs.getLibrary("chottulink"))
            }
        }
}