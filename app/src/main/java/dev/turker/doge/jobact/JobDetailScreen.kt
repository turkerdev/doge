package dev.turker.doge.jobact

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import dev.turker.doge.job.JobContent
import dev.turker.doge.job.JobDTO
import dev.turker.doge.supabaseClient
import dev.turker.doge.ui.theme.Primary
import io.github.jan.supabase.gotrue.gotrue
import io.github.jan.supabase.postgrest.postgrest


@Composable
fun JobDetailScreen(navController: NavController,jobId:Int){
    var job by remember { mutableStateOf<JobDTO?>(null) }

    LaunchedEffect(Unit){
        job = supabaseClient.postgrest["ilanlar"]
            .select{
                eq("id", jobId)}
            .decodeSingle()
    }

    if (job == null)
    {
        Text(text = "Yukleniyor...")
    }
    else {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Row {
                    Text(text = "${job!!.kopekAdi} / ${job!!.kopekCinsi}", fontSize = 24.sp)
                }
                Text(text = job!!.aciklama)
            }
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 16.dp,
                        end = 16.dp,
                        top = 20.dp,
                        bottom = 20.dp
                    ),
                colors = ButtonDefaults.buttonColors(contentColor = Color.Black, containerColor = Primary),
                onClick = { navController.navigate("/iletisim") })
            {
                Text(text = "iletişime geç")
            }
        }
    }
}
