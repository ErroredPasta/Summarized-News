import com.example.buildsrc.*

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    namespace = "com.example.summarizednews"
    compileSdk = COMPILE_SDK

    defaultConfig {
        applicationId = APPLICATION_ID
        minSdk = MIN_SDK
        targetSdk = TARGET_SDK
        versionCode = VERSION_CODE
        versionName = VERSION_NAME

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        dataBinding = true
        viewBinding = true
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.9.0")
    implementation(APP_COMPAT)
    implementation("com.google.android.material:material:1.8.0")
    implementation(CONSTRAINT_LAYOUT)
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    retrofitBundle.forEach { implementation(it) }
    navigationBundle.forEach { implementation(it) }

    implementation(COROUTINE_ANDROID)

    implementation(HILT_ANDROID)
    kapt(HILT_COMPILER)

    implementation(project(":core:core_ui"))
    implementation(project(":core:core_util"))

    implementation(project(":news:news_domain"))
    implementation(project(":news:news_ui"))
    implementation(project(":news:news_data"))

    implementation(project(":summary:summary_domain"))
    implementation(project(":summary:summary_data"))
}

kapt {
    correctErrorTypes = true
}