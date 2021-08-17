pluginManagement {

    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

plugins {
    id("de.fayard.refreshVersions") version "0.11.0"
}

refreshVersions {
    featureFlags {
        enable(de.fayard.refreshVersions.core.FeatureFlag.LIBS)
        disable(de.fayard.refreshVersions.core.FeatureFlag.GRADLE_UPDATES)
    }
    enableBuildSrcLibs()
}
rootProject.name = "Kotlin Base Template"
include(":app")
