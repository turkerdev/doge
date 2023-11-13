package dev.turker.doge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.turker.doge.ui.theme.DogeTheme
import dev.turker.doge.ui.theme.Primary
import dev.turker.doge.ui.theme.SurfaceContainer
import io.github.jan.supabase.gotrue.gotrue
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Returning
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.logging.Logger

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            DogeTheme {
                val navController = rememberNavController()
                Scaffold (bottomBar = { NavBar(navController) } )
                { innerPadding ->
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                    ) {
                        NavHost(navController = navController, startDestination = "/"){
                            composable("/"){KendiIlanlar(navController)}
                            composable("/ilanekle"){ IlanEkle(navController)}
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun NavBar(navController: NavController){
    var selectedItem by remember { mutableStateOf(0)}
    val items = listOf("/", "/ilanekle")

    NavigationBar(containerColor = SurfaceContainer) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Primary
                ),
                icon = { Icon(Icons.Filled.Edit, contentDescription = item) },
                label = { Text(item) },
                selected = selectedItem == index,
                onClick = {
                    selectedItem = index
                    navController.navigate(items[selectedItem])
                }
            )
        }
    }
}

@Serializable
data class YeniIlan(
    @SerialName("kopek_adi")
    val kopekAdi: String,
    @SerialName("kopek_cinsi")
    val kopekCinsi: String,
    @SerialName("aciklama")
    val aciklama: String,
    @SerialName("user_id")
    val userid:String
)

@Serializable
data class Ilan(
    @SerialName("id")
    val id:Int,
    @SerialName("kopek_adi")
    val kopekAdi: String,
    @SerialName("kopek_cinsi")
    val kopekCinsi: String,
    @SerialName("aciklama")
    val aciklama: String,
    @SerialName("user_id")
    val userid:String
)

@Composable
fun ilanContent(ilan:Ilan)
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
                text = ilan.kopekAdi,
            )
            Text(
                text = ilan.kopekCinsi,
            )
            Text(
                text = ilan.aciklama,
            )
        }
    }
}

@Composable
fun KendiIlanlar(navController: NavController){
    var ilanlar = remember { mutableStateListOf<Ilan>() }

    LaunchedEffect(Unit){
        val ilanListesi = supabaseClient.postgrest["ilanlar"].select{
            eq("user_id",supabaseClient.gotrue.currentSessionOrNull()?.user!!.id)
        }.decodeList<Ilan>()
        ilanListesi.forEach{ilanlar.add(it)};
    }

    Column {
        ilanlar.forEach{
            ilanContent(ilan = it)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IlanEkle(navController: NavController){
    var isim by remember { mutableStateOf("") }
    var tur by remember { mutableStateOf("") }
    var aciklama by remember { mutableStateOf("") }

    fun ekle(){
        CoroutineScope(Dispatchers.IO).launch {
            val ilan = YeniIlan(kopekAdi = isim,kopekCinsi=tur,aciklama=aciklama,userid=supabaseClient.gotrue.currentSessionOrNull()?.user!!.id)
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