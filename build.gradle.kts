// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.44.2")

        val navVersion = "2.5.3"
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:$navVersion")

        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.20")
    }
}

plugins {
    id("com.android.application") version("7.3.1") apply(false)
    id("com.android.library") version("7.3.1") apply(false)
    id("org.jetbrains.kotlin.android") version("1.8.20") apply(false)
    id("org.jetbrains.kotlin.jvm") version("1.8.20") apply(false)
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}