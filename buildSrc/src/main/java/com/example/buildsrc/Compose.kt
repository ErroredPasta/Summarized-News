package com.example.buildsrc

import org.gradle.kotlin.dsl.DependencyHandlerScope

const val COMPOSE_VERSION = "1.4.6"

private const val COMPOSE_BOM_VERSION = "2023.04.01"
const val COMPOSE_BOM = "androidx.compose:compose-bom:$COMPOSE_BOM_VERSION"

const val MATERIAL_DESIGN_2 = "androidx.compose.material:material"

const val COMPOSE_PREVIEW = "androidx.compose.ui:ui-tooling-preview"
const val COMPOSE_PREVIEW_DEBUG = "androidx.compose.ui:ui-tooling"

private const val COMPOSE_ACTIVITY_VERSION = "1.6.1"
const val COMPOSE_ACTIVITY = "androidx.activity:activity-compose:$COMPOSE_ACTIVITY_VERSION"

private const val LIFECYCLE_COMPOSE_VERSION = "2.5.1"
const val COMPOSE_VIEW_MODEL = "androidx.lifecycle:lifecycle-viewmodel-compose:$LIFECYCLE_COMPOSE_VERSION"
const val LIFECYCLE_COMPOSE = "androidx.lifecycle:lifecycle-runtime-compose:$LIFECYCLE_COMPOSE_VERSION"

fun DependencyHandlerScope.applyCompose() {
    val composeBom = platform(COMPOSE_BOM)
    "implementation"(composeBom)
    "androidTestImplementation"(composeBom)

    "implementation"(MATERIAL_DESIGN_2)

    "implementation"(COMPOSE_PREVIEW)
    "debugImplementation"(COMPOSE_PREVIEW_DEBUG)

    "implementation"(COMPOSE_ACTIVITY)
    "implementation"(COMPOSE_VIEW_MODEL)
    "implementation"(LIFECYCLE_COMPOSE)
}