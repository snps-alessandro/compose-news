plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
    id("kotlin-parcelize")
}

android {
    compileSdk = Versions.compileSdk
    defaultConfig {
        minSdk = Versions.minSdk
        targetSdk = Versions.targetSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        compose = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    composeOptions {
        kotlinCompilerExtensionVersion =
            de.fayard.refreshVersions.core.versionFor(AndroidX.compose.ui)
    }
}

dependencies {

    implementation(kotlin("stdlib-jdk8"))
    // AndroidX
    implementation(Libs.ui)
    implementation(Libs.ui)
    implementation(Libs.ui_tooling)
    implementation(Libs.foundation)
    implementation(Libs.androidx_compose_material_material)
    implementation(Libs.navigation_compose)
    implementation(Libs.material_icons_extended)
    implementation(Libs.material_icons_core)
    implementation(Libs.hilt_navigation_compose)

    // SquareUP
    implementation(Libs.retrofit)
    implementation(Libs.converter_gson)
    implementation(Libs.okhttp)
    implementation(Libs.logging_interceptor)

    // Google
    implementation(Libs.gson)
    implementation(Libs.hilt_android)
    kapt(Libs.hilt_android_compiler)
}