package dev.turker.doge.ui.posts

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import dev.turker.doge.model.Post
import dev.turker.doge.ui.theme.Primary

@Composable
fun PostCard(post: Post, onPostClick: () -> Unit) {

    Card(
        colors = CardDefaults.cardColors(
            containerColor = Primary,
            contentColor = Color.Black
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .clickable(onClick = onPostClick)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.padding(10.dp)
        ) {
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
                    Text(
                        text = post.dogName,
                    )
                    Text(
                        text = post.dogRace,
                    )
                }
            }
            Text(
                text = post.description,
            )
        }
    }
}