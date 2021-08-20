import de.fayard.refreshVersions.core.versionFor

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
}

android {
    signingConfigs {
        create("staging") {
        }
        create("release") {
        }
    }
    compileSdk = Versions.compileSdk
    buildToolsVersion = Versions.buildTools

    defaultConfig {
        applicationId = Versions.applicationID

        minSdk = Versions.minSdk
        targetSdk = Versions.targetSdk

        versionCode = Versions.versionCode
        versionName = Versions.versionName

        testInstrumentationRunner = "it.alexs.composenews.HiltTestRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        debug {
            versionNameSuffix = "-debug"
            isDebuggable = true
        }

        create("staging") {
            initWith(getByName("debug"))
            versionNameSuffix = "-staging"
            matchingFallbacks.add("debug")
            isDebuggable = true
        }
    }

    testBuildType = "staging"

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
        kotlinCompilerExtensionVersion = versionFor(AndroidX.compose.ui)
    }

    packagingOptions {
        resources.excludes.add("/META-INF/AL2.0")
        resources.excludes.add("/META-INF/LGPL2.1")
    }

    sourceSets {
        getByName("staging") {
            java {
                srcDirs("src\\staging\\java")
            }
        }
    }

    lint {
        isWarningsAsErrors = true
        isAbortOnError = true
    }
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    implementation(project(":sharelibs"))
    implementation(project(":article"))
    // AndroidX
    implementation(Libs.appcompat)
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
    implementation(Libs.ui_test_junit4)
    implementation(Libs.camera_camera2)
    implementation(Libs.hilt_navigation_compose)
    implementation(Libs.accompanist_navigation_animation)

    // Google
    implementation(Libs.com_google_android_material_material)
    implementation(Libs.hilt_android)
    implementation(Libs.androidx_test_runner)
    kapt(Libs.hilt_android_compiler)

    // Timber
    implementation(Libs.timber)


    androidTestImplementation(Libs.androidx_test_runner)
    androidTestImplementation(Libs.androidx_test_rules)
    androidTestImplementation(Libs.espresso_core)
    androidTestImplementation(Libs.espresso_idling_resource)

    androidTestImplementation(Libs.ui_test)
    androidTestImplementation(Libs.ui_test_junit4)
    debugImplementation(Libs.ui_test_manifest)

    androidTestImplementation(Libs.hilt_android_testing)
    kaptAndroidTest(Libs.hilt_android_compiler)
}

kapt {
    correctErrorTypes = true
}
