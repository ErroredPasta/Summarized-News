import com.example.buildsrc.*

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    namespace = "com.example.news_ui"
    compileSdk = COMPILE_SDK

    defaultConfig {
        minSdk = MIN_SDK
        targetSdk = TARGET_SDK

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        dataBinding = true
        viewBinding = true
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = COMPOSE_VERSION
    }
}

dependencies {
    implementation(APP_COMPAT)
    implementation(CONSTRAINT_LAYOUT)
    implementation(SWIPE_REFRESH_LAYOUT)

    implementation(HILT_ANDROID)
    kapt(HILT_COMPILER)

    implementation(FRAGMENT_KTX)
    implementation(PAGING)
    implementation(SHIMMER)

    navigationBundle.forEach { implementation(it) }

    implementation(project(":core:core_ui"))
    implementation(project(":core:core_util"))

    implementation(project(":news:news_domain"))

    implementation(project(":summary:summary_domain"))

    applyCompose()
    implementation(PAGING_COMPOSE)
}

kapt {
    correctErrorTypes = true
}