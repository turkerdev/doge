package dev.turker.doge.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dev.turker.doge.supabaseClient
import dev.turker.doge.ui.theme.DogeTheme
import dev.turker.doge.ui.theme.Primary
import dev.turker.doge.ui.theme.SurfaceContainer
import io.github.jan.supabase.gotrue.SessionStatus
import io.github.jan.supabase.gotrue.gotrue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DogeApp() {
    DogeTheme {
        val navController = rememberNavController()
        val navActions = remember(navController) {
            DogeNavigationActions(navController)
        }

        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        LaunchedEffect(Unit) {
            supabaseClient.gotrue.sessionStatus.collect {
                when (it) {
                    is SessionStatus.Authenticated -> {
                        navActions.navigateTo(DogeRoutes.POSTS_ROUTE)
                    }

                    SessionStatus.NotAuthenticated -> {
                        navActions.navigateTo(DogeRoutes.AUTH_ROUTE)
                    }

                    else -> {}
                }
            }
        }

        Scaffold(bottomBar = {
            NavigationBar(containerColor = SurfaceContainer) {
                DogeNavigation.forEach { route ->
                    NavigationBarItem(
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = Primary
                        ),
                        icon = { Icon(imageVector = route.icon, contentDescription = "") },
                        label = { Text(route.text) },
                        selected = currentRoute == route.path,
                        onClick = {
                            navActions.navigateTo(route.path)
                        }
                    )
                }
            }
        })
        { innerPadding ->
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
            ) {
                DogeNavHost(navController, navActions)
            }
        }
    }
}