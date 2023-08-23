pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }

    plugins {
        val kotlinVersion = extra["kotlin.version"] as String
        val agpVersion = extra["agp.version"] as String
        val composeVersion = extra["compose.version"] as String

        kotlin("jvm").version(kotlinVersion)
        kotlin("multiplatform").version(kotlinVersion)
        kotlin("android").version(kotlinVersion)
        id("com.android.base").version(agpVersion)
        id("com.android.application").version(agpVersion)
        id("com.android.library").version(agpVersion)
        id("org.jetbrains.compose").version(composeVersion)
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "PersonalFinance"
include(":androidApp")
include(":shared")
include(":desktopApp")
include(":domain")
include(":features")
include(":common")
include(":common:resources")
include(":common:utils")
include(":data")
include(":common:navigation")
include(":features:authentication")
include(":features:transaction")
include(":features:category")
include(":features:Main")
include(":features:Status")
include(":features:Accounts")
include(":features:More")
