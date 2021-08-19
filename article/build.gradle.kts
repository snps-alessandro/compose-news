plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
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
        viewBinding = true
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
    implementation(project(":sharelibs"))
    // AndroidX
    implementation(Libs.ui)
    implementation(Libs.ui_tooling)
    implementation(Libs.foundation)
    implementation(Libs.androidx_compose_material_material)
    implementation(Libs.material_icons_extended)
    implementation(Libs.material_icons_core)
    implementation(Libs.activity_compose)
    implementation(Libs.lifecycle_viewmodel_compose)
    implementation(Libs.navigation_compose)
    implementation(Libs.runtime_livedata)
    implementation(Libs.hilt_navigation_compose)
    implementation(Libs.accompanist_placeholder)
    implementation(Libs.accompanist_swiperefresh)
    implementation(Libs.coil_compose)
    implementation(Libs.joda_time)

    implementation(Libs.hilt_android)
    kapt(Libs.hilt_android_compiler)
}