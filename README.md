 # kotlin-base-template

A simple Github template that lets you create an **Android/Kotlin** project and be up and running in a **few seconds**. 

## How to use

Just click on [Use this template](https://github.com/AlessandroSpasiano/kotlin-base-template) button to create a new repo starting from this template.

Once created don't forget to update the:
- [App ID](buildSrc/src/main/java/Versions.kt)
- AndroidManifest ([here](app/src/main/AndroidManifest.xml)
- Package of the source files

## Features

- 100% Kotlin-only template.
- 1 Sample modules (Android app).
- 100% Gradle Kotlin DSL setup.
- Dependency versions managed via `buildSrc`.
- Kotlin Static Analysis via `ktlint` and `detekt`.
- Android Jetpack Compose.

## Gradle Setup

This template is using [Gradle Kotlin DSL](https://docs.gradle.org/current/userguide/kotlin_dsl.html) as well as the [Plugin DSL](https://docs.gradle.org/current/userguide/plugins.html#sec:plugins_block) to setup the build.

All dependencies are centralized inside the [Versions.kt](buildSrc/src/main/java/Versions.kt) file in the `buildSrc` folder. This provides convenient auto-completion when writing your gradle files.

## Static Analysis

This template is using [ktlint](https://github.com/pinterest/ktlint) with the [ktlint-gradle](https://github.com/jlleitschuh/ktlint-gradle) plugin to format your code. To reformat all the source code as well as the buildscript you can run the `ktlintFormat` gradle task.

This template is also using [detekt](https://github.com/arturbosch/detekt) to analyze the source code, with the configuration that is stored in the [detekt.yml](config/detekt/detekt.yml) file (the file has been generated with the `detektGenerateConfig` task).

## Android Jetpack Compose
This template is using [Android Jetpack Compose](https://developer.android.com/jetpack/compose) to build native UI.