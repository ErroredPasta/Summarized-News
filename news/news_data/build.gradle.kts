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
    implementation(PAGING_COMMON)

    implementation(project(":core:core_util"))
    implementation(project(":news:news_domain"))
}