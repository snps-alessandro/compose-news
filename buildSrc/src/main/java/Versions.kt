object Versions {

    const val applicationID = "it.alexs.kotlinbasetemplate"
    const val compileSdk = 29
    const val minSdk = 24
    const val targetSdk = 30
    const val androidGradlePlugin = "3.3.2"
    const val buildTools = "30.0.2"
    const val versionName = "1.0.0"
    const val versionCode = 1
    const val gradle = "7.0.0-alpha04"

    object Kotlin {
        const val kotlin = "1.4.21"
        const val core = "1.1.0"
        const val coroutines = "1.3.5"
    }

    object KTlint {
        const val ktlint = "0.40.0"
        const val plugin = "9.4.1"
    }

    object Detekt {
        const val detekt = "1.14.2"
    }

    object Support {
        const val legacySupport = "1.0.0"
    }

    object Google {
        const val services = "4.3.4"
        const val vision = "20.1.3"
        const val material = "1.2.1"
        const val truth = "1.1"
        const val gson = "2.8.6"
    }

    object Guava {
        const val guava = "28.2-android"
    }

    object AndroidX {
        const val appCompat = "1.2.0"
        const val constraintLayout = "2.0.4"
        const val recyclerView = "1.0.0"
        const val cardView = "1.0.0"
        const val preference = "1.1.1"
        const val viewPager = "1.0.0"
        const val cameraX = "1.0.0-rc01"
        const val cameraView = "1.0.0-alpha20"
        const val cameraExtensions = "1.0.0-alpha20"
        const val swipeRefresh = "1.1.0"
        const val lifecycle = "2.2.0"
        const val room = "2.2.6"
        const val fragmentKtx = "1.2.5"

        object Compose {
            const val compose = "1.0.0-alpha08"
        }

        object Test {
            const val testing = "2.1.0"
            const val jUnitExt = "1.1.2"
            const val espresso = "3.3.0"
            const val rules = "1.3.0"
            const val runner = "1.3.0"
            const val intents = "3.3.0"
            const val automator = "2.2.0"
        }
    }

    object Firebase {
        const val core = "16.0.8"
        const val analytics = "17.2.1"
        const val crashlytics = "2.10.1"
    }

    object Squareup {
        const val picasso = "2.71828"
        const val downloader = "1.1.0"
        const val retrofit = "2.9.0"
        const val okhttp = "4.9.0"
    }

    object JodaTime {
        const val joda = "2.10.9"
    }

    object Timber {
        const val timber = "4.7.1"
    }

    object Chucker {
        const val library = "3.4.0"
    }

    object Test {
        const val mockito = "3.7.0"
        const val jUnit = "4.13.1"
    }
}

object Libs {

    object Kotlin {
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.Kotlin.kotlin}"
    }

    object Joda {
        const val jodaTime = "joda-time:joda-time:${Versions.JodaTime.joda}"
    }

    object AndroidX {
        const val appcompat = "androidx.appcompat:appcompat:${Versions.AndroidX.appCompat}"
        const val preference = "androidx.preference:preference-ktx:${Versions.AndroidX.preference}"
        const val cardview = "androidx.cardview:cardview:${Versions.AndroidX.cardView}"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.AndroidX.constraintLayout}"
        const val swipeRefresh = "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.AndroidX.swipeRefresh}"
        const val fragment = "androidx.fragment:fragment-ktx:${Versions.AndroidX.fragmentKtx}"

        object Lifecycle {
            const val livedataKtx = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.AndroidX.lifecycle}"
            const val viewmodelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.AndroidX.lifecycle}"
        }

        object Room {
            const val runtime = "androidx.room:room-runtime:${Versions.AndroidX.room}"
            const val ktx = "androidx.room:room-ktx:${Versions.AndroidX.room}"
            const val compiler = "androidx.room:room-compiler:${Versions.AndroidX.room}"
        }

        object CameraX {
            const val cameraX = "androidx.camera:camera-camera2:${Versions.AndroidX.cameraX}"
            const val view = "androidx.camera:camera-view:${Versions.AndroidX.cameraView}"
            const val extensions = "androidx.camera:camera-extensions:${Versions.AndroidX.cameraExtensions}"
            const val lifecycle = "androidx.camera:camera-lifecycle:${Versions.AndroidX.cameraX}"
            const val core = "androidx.camera:camera-core:${Versions.AndroidX.cameraX}"
        }

        object Compose {
            const val ui = "androidx.compose.ui:ui:${Versions.AndroidX.Compose.compose}"
            const val tooling = "androidx.compose.ui:ui-tooling:${Versions.AndroidX.Compose.compose}"
            const val foundation = "androidx.compose.foundation:foundation:${Versions.AndroidX.Compose.compose}"
            const val material = "androidx.compose.material:material:${Versions.AndroidX.Compose.compose}"
            const val icon = "androidx.compose.material:material-icons-core:${Versions.AndroidX.Compose.compose}"
            const val iconExtended = "androidx.compose.material:material-icons-extended:${Versions.AndroidX.Compose.compose}"
            const val livedata = "androidx.compose.runtime:runtime-livedata:${Versions.AndroidX.Compose.compose}"
            const val test = "androidx.compose.ui:ui-test-junit4:${Versions.AndroidX.Compose.compose}"
        }

        object Test {
            const val testing = "androidx.arch.core:core-testing:${Versions.AndroidX.Test.testing}"
            const val jUnitExt = "androidx.test.ext:junit:${Versions.AndroidX.Test.jUnitExt}"
            const val runner = "androidx.test:runner:${Versions.AndroidX.Test.runner}"
            const val rules = "androidx.test:rules:${Versions.AndroidX.Test.rules}"
            const val espresso = "androidx.test.espresso:espresso-core:${Versions.AndroidX.Test.espresso}"
            const val espressoContrib = "androidx.test.espresso:espresso-contrib:${Versions.AndroidX.Test.espresso}"
            const val intents = "androidx.test.espresso:espresso-idling-resource:${Versions.AndroidX.Test.intents}"
            const val automator = "androidx.test.uiautomator:uiautomator:${Versions.AndroidX.Test.automator}"
        }
    }

    object Google {
        const val material = "com.google.android.material:material:${Versions.Google.material}"
        const val vision = "com.google.android.gms:play-services-vision:${Versions.Google.vision}"
        const val truth = "com.google.truth:truth:${Versions.Google.truth}"
        const val gson = "com.google.code.gson:gson:${Versions.Google.gson}"
    }

    object Timber {
        const val timber = "com.jakewharton.timber:timber:${Versions.Timber.timber}"
    }

    object Squareup {

        const val picasso = "com.squareup.picasso:picasso:${Versions.Squareup.picasso}"
        const val downloader = "com.jakewharton.picasso:picasso2-okhttp3-downloader:${Versions.Squareup.downloader}"

        object Retrofit {
            const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.Squareup.retrofit}"
            const val gson = "com.squareup.retrofit2:converter-gson:${Versions.Squareup.retrofit}"
            const val interceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.Squareup.okhttp}"
        }
    }

    object Chucker {
        const val chucker = "com.github.chuckerteam.chucker:library:${Versions.Chucker.library}"
        const val noop = "com.github.chuckerteam.chucker:library-no-op:${Versions.Chucker.library}"
    }

    object Test {
        const val jUnit = "junit:junit:${Versions.Test.jUnit}"
        const val mockito = "org.mockito:mockito-core:${Versions.Test.mockito}"
    }
}