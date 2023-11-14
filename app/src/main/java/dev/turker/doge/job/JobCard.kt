package dev.turker.doge.job

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
import dev.turker.doge.ui.theme.Primary

@Composable
fun JobContent(job: JobDTO)
{
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Primary,
            contentColor = Color.Black
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.padding(10.dp)
        ) {
            Text(
                text = job.kopekAdi,
            )
            Text(
                text = job.kopekCinsi,
            )
            Text(
                text = job.aciklama,
            )
        }
    }
}