package com.example.news_ui

import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.Navigator

sealed class Route(val route: String)

sealed class Destination(route: String) : Route(route) {
    object NewsListDestination : Destination("news")

    data class NewsDetailDestination(val newsId: String) : Destination(
        "news/detail?news_id=$newsId"
    ) {
        companion object : Route("news/detail?news_id={news_id}")
    }
}

fun NavHostController.navigate(
    destination: Destination,
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator.Extras? = null
) = navigate(destination.route, navOptions, navigatorExtras)