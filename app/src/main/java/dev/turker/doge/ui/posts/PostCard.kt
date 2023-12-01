package dev.turker.doge.ui.posts

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.turker.doge.model.Post
import dev.turker.doge.ui.theme.Primary

@Composable
fun PostCard(job: Post, onPostClick: () -> Unit) {

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
            Text(
                text = job.dogName,
            )
            Text(
                text = job.dogRace,
            )
            Text(
                text = job.description,
            )
        }
    }
}