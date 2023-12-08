package dev.turker.doge.ui.post

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import dev.turker.doge.R
import dev.turker.doge.data.PostRepository
import dev.turker.doge.supabaseClient
import dev.turker.doge.ui.DogeNavigationActions
import dev.turker.doge.ui.DogeRoutes
import dev.turker.doge.ui.component.DogeButton
import io.github.jan.supabase.gotrue.gotrue
import kotlinx.coroutines.runBlocking

@Composable
fun PostScreen(navActions: DogeNavigationActions, postId: String) {
    val post = runBlocking {
        PostRepository().getPost(postId)
    }

    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.padding(10.dp)
    ) {
        Column {
            Row {
                AsyncImage(
                    ImageRequest.Builder(LocalContext.current)
                        .data("https://xxctovzmgkwnmxgbzysp.supabase.co/storage/v1/object/public/${post.dogPhoto}")
                        .crossfade(true)
                        .transformations(CircleCropTransformation())
                        .build(),
                    "",
                    modifier = Modifier
                        .width(64.dp)
                        .height(64.dp)
                        .padding(end = 16.dp),
                )
                Column {
                    Text(post.dogName, fontSize = 24.sp)
                    Text(post.dogRace, fontSize = 16.sp)
                }
            }
            Text(text = post.description)
        }
        if (post.userId != supabaseClient.gotrue.currentUserOrNull()?.id) {
            DogeButton(
                onClick = { navActions.navigateTo(DogeRoutes.COMM_ROUTE.replace("{id}", postId)) },
                modifier = Modifier.align(CenterHorizontally)
            ) {
                Text(text = stringResource(R.string.post_communicate))
            }
        }
    }
}