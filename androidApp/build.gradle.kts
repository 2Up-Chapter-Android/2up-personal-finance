plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    namespace = "com.twoup.personalfinance.android"
    val androidMinSdk: String by project
    val androidCompileSdk: String by project
    val androidTargetSdk: String by project
    compileSdk = androidCompileSdk.toInt()
    defaultConfig {
        applicationId = "com.twoup.personalfinance"
        minSdk = androidMinSdk.toInt()
        targetSdk = androidTargetSdk.toInt()
        versionCode = 1
        versionName = "1.0"
        compileSdkPreview = "UpsideDownCake"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.androidx.compose.compiler.get()
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    implementation(project(":shared"))
    implementation(libs.bundles.jetpack.compose)
    //Coroutines
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)
    //DI
    implementation(libs.koin.core)
    implementation(libs.koin.android)
    //Navigation
    implementation(libs.voyager.navigator)
    implementation(libs.hawk)
}
