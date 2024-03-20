plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("org.jetbrains.compose")
}

@OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)
kotlin {
//    targetHierarchy.default()

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
            baseName = "Status"
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                //put your multiplatform dependencies here
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

                implementation("io.github.thechance101:chart:Beta-0.0.5")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }

        val androidMain by getting {
            kotlin.srcDirs("src/jvmMain/kotlin")
            dependsOn(commonMain)
            dependencies {

            }
        }
        val androidUnitTest by getting
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
            dependencies {
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
    namespace = "com.aicontent.status"
    compileSdk = 33
    defaultConfig {
        minSdk = 24
    }
}