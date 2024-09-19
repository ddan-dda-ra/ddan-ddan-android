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

/**
 * Android Application 모듈에 적용할 Plugin
 *
 * plugin id : [ddanddan.android.application]
 */
class AndroidApplicationPlugin : Plugin<Project> {
    override fun apply(target: Project) =
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
                apply("androidx.navigation.safeargs")
                apply("com.google.android.gms.oss-licenses-plugin")
//                apply("com.google.gms.google-services")
//                apply("com.google.firebase.appdistribution")
//                apply("com.google.firebase.crashlytics")
            }


            extensions.configure<ApplicationExtension> {
                namespace = Constants.packageName
                compileSdk = Constants.compileSdk

                configureAndroidCommonPlugin()
                configureDefault()

               packagingOptions {
                    exclude("META-INF/DEPENDENCIES")
                   exclude("migrateToAndroidx/migration.xml")
                }

                buildFeatures {
                    buildConfig = true
                    viewBinding = true
                    dataBinding = true
                    compose = true
                }

                buildTypes {
                    debug {
                        buildConfigField(
                            "String",
                            "BASE_URL",
                            gradleLocalProperties(rootDir).getProperty("base.url"),
                        )
//                        buildConfigField(
//                            "String",
//                            "KAKAO_REDIRECT_URL",
//                            gradleLocalProperties(rootDir).getProperty("kakao.redirect"),
//                        )
//                        buildConfigField(
//                            "String",
//                            "KAKAO_API_KEY",
//                            gradleLocalProperties(rootDir).getProperty("kakao.key"),
//                        )
                    }

                    release {
                        buildConfigField(
                            "String",
                            "BASE_URL",
                            gradleLocalProperties(rootDir).getProperty("base.url"),
                        )
//                        buildConfigField(
//                            "String",
//                            "KAKAO_REDIRECT_URL",
//                            gradleLocalProperties(rootDir).getProperty("kakao.redirect"),
//                        )
//                        buildConfigField(
//                            "String",
//                            "KAKAO_API_KEY",
//                            gradleLocalProperties(rootDir).getProperty("kakao.key"),
//                        )

                        isMinifyEnabled = false
                        proguardFiles(
                            getDefaultProguardFile("proguard-android-optimize.txt"),
                            "proguard-rules.pro",
                        )
                    }
                }

                compileOptions {
                    sourceCompatibility = JavaVersion.VERSION_17
                    targetCompatibility = JavaVersion.VERSION_17
                }

                composeOptions {
                    kotlinCompilerExtensionVersion = "1.4.6"
                }


            }

            val libs = extensions.getVersionCatalog()
            dependencies {
                // androidx
                implementation(libs.getBundle("androidx"))

//                // firebase
                implementation(platform(libs.getLibrary("firebase-bom")))
                implementation(libs.getBundle("firebase"))

                // flipper
                implementation(libs.getBundle("flipper"))

                // retrofit
                implementation(libs.getBundle("retrofit"))

                // test
                testImplementation(libs.getLibrary("jUnit"))
                debugImplementation(libs.getLibrary("ui-tooling-compose"))
                androidTestImplementation(libs.getLibrary("androidTest"))
                androidTestImplementation(libs.getLibrary("espresso"))

//                // google
//                implementation(libs.getLibrary("inAppUpdate"))
                implementation(libs.getLibrary("ossLicense"))
                implementation(libs.getLibrary("gson"))

                // okhttp
                implementation(platform(libs.getLibrary("okhttp-Bom")))
                implementation(libs.getBundle("okhttp"))

                implementation(libs.getBundle("compose"))

//                // kakao
//                implementation(libs.getBundle("kakao"))

                // hilt
                implementation(libs.getLibrary("hilt"))
                kapt(libs.getLibrary("hiltAndroidCompiler"))
                kapt(libs.getLibrary("hiltWorkManagerCompiler"))

                implementation(libs.getBundle("appModuleLibraryEtc"))
            }
        }
}
