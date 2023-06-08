package com.example.summarizednews

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.core_ui.compose.SummarizedNewsTheme
import com.example.news_ui.Destination
import com.example.news_ui.navigate
import com.example.news_ui.screen.detail.NewsDetailScreen
import com.example.news_ui.screen.list.NewsListScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SummarizedNewsTheme {
                Surface {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Destination.NewsListDestination.route
                    ) {
                        composable(Destination.NewsListDestination.route) {
                            NewsListScreen(
                                onNewsItemClick = { id ->
                                    navController.navigate(Destination.NewsDetailDestination(newsId = id))
                                },
                                modifier = Modifier.fillMaxSize()
                            )
                        }

                        composable(
                            Destination.NewsDetailDestination.route,
                            arguments = listOf(navArgument("news_id") { type = NavType.StringType })
                        ) {
                            NewsDetailScreen(modifier = Modifier.fillMaxSize())
                        }
                    }
                }
            }
        }
    }
}