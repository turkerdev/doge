package dev.turker.doge.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController

object DogeRoutes {
    const val ROOT_ROUTE = "root"
    const val AUTH_ROUTE = "auth"
    const val POSTS_ROUTE = "posts"
    const val POST_ROUTE = "post/{id}"
    const val COMM_ROUTE = "comm/{id}"
    const val CREATE_ROUTE = "create"
    const val NOTIF_ROUTE = "notif"
}

data class DogeRoute(
    val path: String,
    val text: String,
    val icon: ImageVector
)

class DogeNavigationActions(private val navController: NavHostController) {
    fun navigateTo(destination: String) {
        navController.navigate(destination) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true

                if (navController.currentDestination?.route == DogeRoutes.ROOT_ROUTE ||
                    navController.currentDestination?.route == DogeRoutes.AUTH_ROUTE
                )
                    inclusive = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
}

val DogeNavigation = listOf(
    DogeRoute(
        path = DogeRoutes.POSTS_ROUTE,
        text = "ilanlar",
        icon = Icons.Filled.Home
    ),
    DogeRoute(
        path = DogeRoutes.CREATE_ROUTE,
        text = "ilan ekle",
        icon = Icons.Filled.Edit
    ),
    DogeRoute(
        path = DogeRoutes.NOTIF_ROUTE,
        text = "bildirimler",
        icon = Icons.Filled.Email
    )
)