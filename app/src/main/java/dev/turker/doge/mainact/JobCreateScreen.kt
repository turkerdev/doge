package dev.turker.doge.mainact

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import dev.turker.doge.job.JobCreateDTO
import dev.turker.doge.supabaseClient
import io.github.jan.supabase.gotrue.gotrue
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JobCreateScreen(navController: NavController){
    var isim by remember { mutableStateOf("") }
    var tur by remember { mutableStateOf("") }
    var aciklama by remember { mutableStateOf("") }

    fun ekle(){
        CoroutineScope(Dispatchers.IO).launch {
            val ilan = JobCreateDTO(kopekAdi = isim,kopekCinsi=tur,aciklama=aciklama,userid= supabaseClient.gotrue.currentSessionOrNull()?.user!!.id)
            supabaseClient.postgrest["ilanlar"].insert(ilan)
        }
        navController.navigate("/")
    }

    Column {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 10.dp, bottom = 10.dp)
                .shadow(elevation = 3.dp, shape = RoundedCornerShape(8.dp)),
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                containerColor = Color.White
            ),
            label = {
                Text(text = "Kopek adi")
            },
            value = isim,
            onValueChange = {isim=it}
        )
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 10.dp, bottom = 10.dp)
                .shadow(elevation = 3.dp, shape = RoundedCornerShape(8.dp)),
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                containerColor = Color.White
            ),
            label = {
                Text(text = "Kopek cinsi")
            },
            value = tur,
            onValueChange = {tur=it}
        )
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 10.dp, bottom = 10.dp)
                .shadow(elevation = 3.dp, shape = RoundedCornerShape(8.dp)),
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                containerColor = Color.White
            ),
            label = {
                Text(text = "Aciklama")
            },
            value = aciklama,
            onValueChange = {aciklama=it}
        )
        Button(modifier = Modifier
            .padding(
                start = 16.dp,
                end = 16.dp,
                top = 10.dp,
                bottom = 10.dp
            )
            .shadow(elevation = 3.dp, shape = RoundedCornerShape(64.dp)),
            colors = ButtonDefaults.buttonColors(contentColor = Color.Black, containerColor = Color.White),
            onClick = {ekle()})
        {
            Text(text = "ekle")
        }
    }
}