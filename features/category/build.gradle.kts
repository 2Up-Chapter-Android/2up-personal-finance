plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("org.jetbrains.compose")
    id("dev.icerock.mobile.multiplatform-resources")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

kotlin {
//    android {
//        compilations.all {
//            kotlinOptions {
//                jvmTarget = "1.8"
//            }
//        }
//    }

    android()

    jvm("desktop")

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "category"
        }
    }

    sourceSets {
        val commonMain by getting{
            dependencies {
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
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
            dependencies {
                dependsOn(commonMain)

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
    namespace = "com.aicontent.category"
    val androidMinSdk: String by project
    val androidCompileSdk: String by project
    val androidTargetSdk: String by project
//    compileSdk = 33
//    defaultConfig {
//        minSdk = 24
//    }
    compileSdk = androidCompileSdk.toInt()
    defaultConfig {
        minSdk = androidMinSdk.toInt()
    }
}
