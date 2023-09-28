plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("org.jetbrains.compose")
    id("dev.icerock.mobile.multiplatform-resources")
}

@OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)
kotlin {
    targetHierarchy.default()

    android {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }

    jvm("desktop")
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "Accounts"
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies{
                implementation(project(":data"))
                implementation(project(":domain"))
                implementation(project(":common:navigation"))
                implementation(project(":common:utils"))
                //Compose
                implementation(compose.ui)
                implementation(compose.foundation)
                implementation(compose.material)
                implementation(compose.runtime)
                implementation(libs.moko.compose.resource)
                //Coroutines
                implementation(libs.kotlinx.coroutines.core)
                // DI
                api(libs.koin.core)
                //Navigation
                implementation(libs.voyager.navigator)
                //Logging
                implementation(libs.napier)
                //Kotlinx Date time
                implementation(libs.datetime)

            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }

        val desktopMain by getting {
            kotlin.srcDirs("src/jvmMain/kotlin")
            dependsOn(commonMain)
            dependencies {
            }
        }
    }
}

android {
    namespace = "com.aicontent.accounts"
    val androidMinSdk: String by project
    val androidCompileSdk: String by project
    val androidTargetSdk: String by project

    compileSdk = androidCompileSdk.toInt()
    defaultConfig {
        minSdk = androidMinSdk.toInt()
    }
}