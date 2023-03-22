import com.example.buildsrc.*

plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    implementation(RETROFIT)
    implementation(RETROFIT_GSON_CONVERTER)
    implementation(JAVAX_INJECT)
    implementation(COROUTINE_CORE)

    implementation(project(":core:core_util"))
    implementation(project(":summary:summary_domain"))
}