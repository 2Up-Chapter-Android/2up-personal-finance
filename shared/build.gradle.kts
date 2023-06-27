plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("com.android.library")
    id("org.jetbrains.compose")
    id("kotlinx-serialization")
    id("dev.icerock.mobile.multiplatform-resources")
}

version = "1.0-SNAPSHOT"

kotlin {
    android {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }

    jvm("desktop")

    ios()
    iosSimulatorArm64()

    cocoapods {
        summary = "Shared code for the sample"
        homepage = "https://github.com/JetBrains/compose-jb"
        ios.deploymentTarget = "14.1"
        podfile = project.file("../iosApp/Podfile")
        framework {
            baseName = "shared"
            isStatic = false
            export(libs.moko.resources)
            export(project(":common:resources"))
        }
        extraSpecAttributes["resources"] = "['src/commonMain/resources/**', 'src/iosMain/resources/**']"
        extraSpecAttributes["exclude_files"] = "['src/commonMain/resources/MR/**']"
    }

    targets.withType<org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget> {
        binaries.withType<org.jetbrains.kotlin.gradle.plugin.mpp.Framework> {
            linkerOpts.add("-lsqlite3")
//            export(libs.kermit)
//            export(libs.hyperdrive.multiplatformx.api)
//            export(project(":shared"))
//            export(project(":shared-ui"))
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":domain"))
                implementation(project(":data"))
                implementation(project(":features:authentication"))
                api(project(":common:resources"))

                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material)
                implementation(compose.materialIconsExtended)
                @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                implementation(compose.components.resources)


                api(libs.kotlinx.coroutines.core)

                implementation(libs.koin.core)

                implementation(compose.ui)
                implementation(compose.runtime)

                implementation(libs.voyager.navigator)
                implementation(libs.voyager.koin)
                implementation(libs.voyager.navigator.tab)

                api(libs.moko.compose.resource)
            }
        }
        val androidMain by getting {
            kotlin.srcDirs("src/jvmMain/kotlin")
            dependencies {
                api(libs.activity.compose)
            }
        }
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by getting {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
            dependencies {
                implementation(compose.ui)
            }
        }

        val desktopMain by getting {
            kotlin.srcDirs("src/jvmMain/kotlin")
            dependsOn(commonMain)
            dependencies {
                implementation(compose.desktop.common)
            }
        }
    }
}

android {
    val androidMinSdk: String by project
    val androidCompileSdk: String by project
    val androidTargetSdk: String by project

    namespace = "com.twoup.personalfinance.common"
    compileSdk = androidCompileSdk.toInt()
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res", "src/commonMain/resources")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")
    sourceSets["main"].resources.exclude("src/commonMain/resources/MR")
    defaultConfig {
        minSdk = androidMinSdk.toInt()
        targetSdk = androidTargetSdk.toInt()
    }
}

multiplatformResources {
    multiplatformResourcesPackage = "com.twoup.personalfinance"
    multiplatformResourcesClassName = "SharedMR"
}

// workaround https://github.com/icerockdev/moko-resources/issues/421
tasks.matching { it.name == "desktopProcessResources" }.configureEach {
    dependsOn(tasks.matching { it.name == "generateMRdesktopMain" })
}
tasks.matching { it.name == "iosSimulatorArm64ProcessResources" }.configureEach {
    dependsOn(tasks.matching { it.name == "generateMRiosSimulatorArm64Main" })
}
tasks.matching { it.name == "metadataIosMainProcessResources" }.configureEach {
    dependsOn(tasks.matching { it.name == "generateMRcommonMain" })
}
tasks.matching { it.name == "metadataCommonMainProcessResources" }.configureEach {
    dependsOn(tasks.matching { it.name == "generateMRcommonMain" })
}
tasks.matching { it.name == "iosX64ProcessResources" }.configureEach {
    dependsOn(tasks.matching { it.name == "generateMRiosX64Main" })
}
tasks.matching { it.name == "iosArm64ProcessResources" }.configureEach {
    dependsOn(tasks.matching { it.name == "generateMRiosArm64Main" })
}