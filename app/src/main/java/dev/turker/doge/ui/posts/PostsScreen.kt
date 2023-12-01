package dev.turker.doge.ui.posts

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import dev.turker.doge.data.PostRepository
import dev.turker.doge.ui.DogeNavigationActions
import dev.turker.doge.ui.DogeRoutes
import kotlinx.coroutines.runBlocking


@Composable
fun PostsScreen(navActions: DogeNavigationActions) {
    val posts = runBlocking {
        PostRepository().getFeed()
    }

    Column {
        posts.forEach {
            PostCard(job = it, onPostClick = {
                navActions.navigateTo(DogeRoutes.POST_ROUTE.replace("{id}", it.id.toString()))
            })
        }
    }
}