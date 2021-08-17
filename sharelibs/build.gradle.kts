plugins {
    id("com.android.library")
    kotlin("android")
}

android {
    compileSdk = Versions.compileSdk
    defaultConfig {
        minSdk = Versions.minSdk
        targetSdk = Versions.targetSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
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
    implementation(Libs.material_icons_extended)
    implementation(Libs.material_icons_core)
}