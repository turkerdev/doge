package dev.turker.doge.ui.post

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.turker.doge.R
import dev.turker.doge.data.PostRepository
import dev.turker.doge.ui.DogeNavigationActions
import dev.turker.doge.ui.DogeRoutes
import dev.turker.doge.ui.component.DogeButton
import kotlinx.coroutines.runBlocking

@Composable
fun PostScreen(navActions: DogeNavigationActions, postId: String) {
    val post = runBlocking {
        PostRepository().getPost(postId)
    }

    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row {
                Text(text = "${post.dogName} / ${post.dogRace}", fontSize = 24.sp)
            }
            Text(text = post.description)
        }
        DogeButton(
            onClick = { navActions.navigateTo(DogeRoutes.COMM_ROUTE.replace("{id}", postId)) })
        {
            Text(text = stringResource(R.string.post_communicate))
        }
    }
}