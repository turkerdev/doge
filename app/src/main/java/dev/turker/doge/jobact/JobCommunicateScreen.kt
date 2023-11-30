package dev.turker.doge.jobact

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import dev.turker.doge.job.JobContent
import dev.turker.doge.job.JobDTO
import dev.turker.doge.supabaseClient
import io.github.jan.supabase.gotrue.gotrue
import io.github.jan.supabase.postgrest.postgrest


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JobCommunicateScreen(navController: NavController,jobId:Int){
    var text by remember { mutableStateOf("") }

    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(start = 16.dp, end = 16.dp, top = 10.dp, bottom = 10.dp)
                .shadow(elevation = 3.dp, shape = RoundedCornerShape(8.dp)),
            label = {
                Text(text = "lütfen iletişim bilgilerinizi giriniz. telefon numarası, email, instagram vb.")
            },
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedLabelColor = Color.Gray,
                containerColor = Color.White
            ),
            value = text,
            onValueChange = {text=it}
        )
        Button(
            modifier = Modifier
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    top = 10.dp,
                    bottom = 10.dp
                )
                .shadow(elevation = 3.dp, shape = RoundedCornerShape(64.dp)),
            colors = ButtonDefaults.buttonColors(contentColor = Color.Black, containerColor = Color.White),
            onClick = { /* TODO */ })
        {
            Text(text = "Gönder")
        }
    }
}