plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
}

android {
    compileSdkVersion(Versions.compileSdk)
    buildToolsVersion(Versions.buildTools)

    defaultConfig {
        applicationId = Versions.applicationID

        minSdkVersion(Versions.minSdk)
        targetSdkVersion(Versions.targetSdk)

        versionCode = Versions.versionCode
        versionName = Versions.versionName
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        getByName("debug") {
            versionNameSuffix = "-debug"
            isDebuggable = true
        }

        create("staging") {
            initWith(getByName("debug"))
            versionNameSuffix = "-staging"
            matchingFallbacks.add("debug")
        }
    }

    testBuildType = "staging"

    kotlinOptions {
        jvmTarget = "1.8"
        useIR = true
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
        kotlinCompilerExtensionVersion = Versions.AndroidX.Compose.compose
        kotlinCompilerVersion = Versions.Kotlin.kotlin
    }

    packagingOptions {
        excludes.add("/META-INF/AL2.0")
        excludes.add("/META-INF/LGPL2.1")
    }

    // dynamicFeatures = mutableSetOf()

    sourceSets {
        getByName("staging") {
            java {
                srcDirs("src\\staging\\java")
            }
        }
    }

    lintOptions {
        isWarningsAsErrors = true
        isAbortOnError = true
    }
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    implementation(Libs.AndroidX.appcompat)
    implementation(Libs.AndroidX.Compose.ui)
    implementation(Libs.AndroidX.Compose.tooling)
    implementation(Libs.AndroidX.Compose.foundation)
    implementation(Libs.AndroidX.Compose.material)
    implementation(Libs.AndroidX.Compose.iconExtended)
    implementation(Libs.AndroidX.Compose.icon)
    implementation(Libs.AndroidX.Compose.livedata)
    implementation(Libs.AndroidX.Compose.test)
    api(Libs.AndroidX.CameraX.cameraX)

    implementation(Libs.Google.material)

    implementation(Libs.Timber.timber)

    testImplementation(Libs.Test.jUnit)
    testImplementation(Libs.Test.mockito)
    testImplementation(Libs.Google.truth)
    testImplementation(Libs.AndroidX.Test.testing)
    implementation(Libs.AndroidX.Test.intents)
    androidTestImplementation(Libs.AndroidX.Test.jUnitExt)
    androidTestImplementation(Libs.AndroidX.Test.runner)
    androidTestImplementation(Libs.AndroidX.Test.rules)
    androidTestImplementation(Libs.AndroidX.Test.espresso)
    androidTestImplementation(Libs.AndroidX.Test.espressoContrib)
}
