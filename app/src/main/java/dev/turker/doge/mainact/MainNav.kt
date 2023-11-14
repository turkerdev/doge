package dev.turker.doge.mainact

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import dev.turker.doge.ui.theme.Primary
import dev.turker.doge.ui.theme.SurfaceContainer

@Composable
fun NavBar(navController: NavController){
    var selectedItem by remember { mutableStateOf(0) }
    val items = listOf("/", "/ilanekle")

    NavigationBar(containerColor = SurfaceContainer) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Primary
                ),
                icon = { Icon(Icons.Filled.Edit, contentDescription = item) },
                label = { Text(item) },
                selected = selectedItem == index,
                onClick = {
                    selectedItem = index
                    navController.navigate(items[selectedItem])
                }
            )
        }
    }
}