package dev.turker.doge.ui.create

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import dev.turker.doge.R
import dev.turker.doge.data.PostRepository
import dev.turker.doge.model.PostCreate
import dev.turker.doge.supabaseClient
import dev.turker.doge.ui.DogeNavigationActions
import dev.turker.doge.ui.DogeRoutes
import dev.turker.doge.ui.component.DogeButton
import dev.turker.doge.ui.component.DogeTextField
import io.github.jan.supabase.gotrue.gotrue
import kotlinx.coroutines.runBlocking

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateScreen(navActions: DogeNavigationActions) {
    var isim by remember { mutableStateOf("") }
    var tur by remember { mutableStateOf("") }
    var aciklama by remember { mutableStateOf("") }

    fun ekle() {
        val post = runBlocking {
            PostRepository().createPost(
                PostCreate(
                    dogName = isim,
                    dogRace = tur,
                    description = aciklama,
                    userId = supabaseClient.gotrue.currentSessionOrNull()?.user!!.id
                )
            )
        }
        navActions.navigateTo(DogeRoutes.POST_ROUTE.replace("{id}", post.id.toString()))
    }
    
    Column {
        DogeTextField(
            placeholder = stringResource(R.string.create_dogName),
            value = isim,
            onValueChange = { isim = it }
        )
        DogeTextField(
            placeholder = stringResource(R.string.create_dogRace),
            value = tur,
            onValueChange = { tur = it }
        )
        DogeTextField(
            placeholder = stringResource(R.string.create_note),
            value = aciklama,
            onValueChange = { aciklama = it }
        )
        DogeButton(
            onClick = { ekle() })
        {
            Text(stringResource(R.string.create_send))
        }
    }
}