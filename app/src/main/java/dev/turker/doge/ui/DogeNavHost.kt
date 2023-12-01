package dev.turker.doge.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import dev.turker.doge.ui.auth.AuthScreen
import dev.turker.doge.ui.comm.CommScreen
import dev.turker.doge.ui.create.CreateScreen
import dev.turker.doge.ui.post.PostScreen
import dev.turker.doge.ui.posts.PostsScreen

@Composable
fun DogeNavHost(navController: NavHostController, navActions: DogeNavigationActions) {
    NavHost(
        navController = navController,
        startDestination = DogeRoutes.ROOT_ROUTE
    ) {
        composable(DogeRoutes.ROOT_ROUTE) {}
        composable(DogeRoutes.AUTH_ROUTE) {
            AuthScreen()
        }
        composable(DogeRoutes.POSTS_ROUTE) {
            PostsScreen(navActions)
        }
        composable(DogeRoutes.POST_ROUTE) { backStackEntry ->
            PostScreen(navActions, backStackEntry.arguments?.getString("id") ?: "")
        }
        composable(DogeRoutes.COMM_ROUTE) { backStackEntry ->
            CommScreen(navActions, backStackEntry.arguments?.getString("id") ?: "")
        }
        composable(DogeRoutes.CREATE_ROUTE) {
            CreateScreen(navActions)
        }
    }
}