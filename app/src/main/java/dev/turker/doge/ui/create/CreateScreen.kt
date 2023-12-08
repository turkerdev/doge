package dev.turker.doge.ui.create

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
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
import java.io.InputStream

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateScreen(navActions: DogeNavigationActions) {
    val ctx = LocalContext.current
    var isim by remember { mutableStateOf("") }
    var tur by remember { mutableStateOf("") }
    var aciklama by remember { mutableStateOf("") }
    var bitmap by remember {
        mutableStateOf<Bitmap?>(null)
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        val inputStream: InputStream? = uri?.let { ctx.contentResolver.openInputStream(it) }
        inputStream?.use { stream ->
            val selectedBitmap = BitmapFactory.decodeStream(stream)
            bitmap = selectedBitmap
        }
    }

    fun ekle() {
        val post = runBlocking {
            val imagePath = PostRepository().uploadImage(bitmap!!)
            PostRepository().createPost(
                PostCreate(
                    dogName = isim,
                    dogRace = tur,
                    dogPhoto = imagePath,
                    description = aciklama,
                    userId = supabaseClient.gotrue.currentSessionOrNull()?.user!!.id
                ),
            )
        }
        navActions.navigateTo(DogeRoutes.POST_ROUTE.replace("{id}", post.id.toString()))
    }

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
        ) {
            DogeButton(onClick = {
                launcher.launch("image/*")
            }) {
                Text(stringResource(R.string.create_photo))
            }
            if (bitmap != null) {
                AsyncImage(
                    ImageRequest.Builder(LocalContext.current)
                        .data(bitmap)
                        .crossfade(true)
                        .transformations(CircleCropTransformation())
                        .build(),
                    "",
                    modifier = Modifier
                        .width(64.dp)
                        .height(64.dp)
                        .padding(end = 16.dp)
                )
            }
        }
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