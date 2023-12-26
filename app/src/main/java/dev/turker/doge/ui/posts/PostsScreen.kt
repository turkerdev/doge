package dev.turker.doge.ui.posts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.turker.doge.data.PostRepository
import dev.turker.doge.ui.DogeNavigationActions
import dev.turker.doge.ui.DogeRoutes
import kotlinx.coroutines.runBlocking


@Composable
fun PostsScreen(navActions: DogeNavigationActions) {
    val listState = rememberLazyListState()
    val posts = runBlocking {
        PostRepository().getFeed()
    }

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        state = listState,
        modifier = Modifier.padding(8.dp, 0.dp)
    ) {
        item {

            Text(
                text = "Mevcut İlanlar",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp
                ),
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .padding(start = 120.dp)
            )
        }


        items(posts) {
            PostCard(post = it, onPostClick = {
                navActions.navigateTo(DogeRoutes.POST_ROUTE.replace("{id}", it.id.toString()))
            })
        }
    }
}