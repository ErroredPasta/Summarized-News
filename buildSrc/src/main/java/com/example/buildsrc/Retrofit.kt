package com.example.buildsrc


private const val RETROFIT_VERSION = "2.9.0"
const val RETROFIT = "com.squareup.retrofit2:retrofit:$RETROFIT_VERSION"
const val RETROFIT_GSON_CONVERTER = "com.squareup.retrofit2:converter-gson:$RETROFIT_VERSION"

private const val OKHTTP_VERSION = "5.0.0-alpha.2"
const val OKHTTP = "com.squareup.okhttp3:okhttp:$OKHTTP_VERSION"
const val OKHTTP_LOGGING_INTERCEPTOR = "com.squareup.okhttp3:logging-interceptor:$OKHTTP_VERSION"


val retrofitBundle = listOf(
    RETROFIT, RETROFIT_GSON_CONVERTER,
    OKHTTP, OKHTTP_LOGGING_INTERCEPTOR
)