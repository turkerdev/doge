package dev.turker.doge.ui.notification

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import dev.turker.doge.R
import dev.turker.doge.data.PostRepository
import dev.turker.doge.data.UserRepository
import dev.turker.doge.model.Comms
import dev.turker.doge.ui.theme.Primary
import kotlinx.coroutines.runBlocking

@Composable
fun NotificationCard(comms: Comms, onPostClick: () -> Unit) {
    val user = runBlocking {
        UserRepository().getUser(comms.sender)
    }
    val post = runBlocking {
        PostRepository().getPost(comms.post.toString())
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.padding(10.dp)
    ) {
        Row {
            AsyncImage(
                ImageRequest.Builder(LocalContext.current)
                    .data("https://xxctovzmgkwnmxgbzysp.supabase.co/storage/v1/object/public/${user.photo}")
                    .crossfade(true)
                    .transformations(CircleCropTransformation())
                    .build(),
                contentDescription = null,
                modifier = Modifier
                    .size(32.dp)
                    .clip(shape = CircleShape)
            )
            Text(
                text = "${user.name} ${user.surname}, ",
            )
            AsyncImage(
                ImageRequest.Builder(LocalContext.current)
                    .data("https://xxctovzmgkwnmxgbzysp.supabase.co/storage/v1/object/public/${post.dogPhoto}")
                    .crossfade(true)
                    .transformations(CircleCropTransformation())
                    .build(),
                contentDescription = null,
                modifier = Modifier
                    .size(32.dp)
                    .clip(shape = CircleShape)
            )
            Text(
                text = " ${post.dogName} için demiş ki:",
            )
        }
        Row {
            Icon(
                painter = painterResource(R.drawable.phone_call),
                "",
                modifier = Modifier.size(16.dp),
            )
            Text(" " + user.phone)
        }
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
            Text(
                text = comms.message,
                color = Color.White,
                modifier = Modifier.padding(12.dp)
            )
        }
    }
}