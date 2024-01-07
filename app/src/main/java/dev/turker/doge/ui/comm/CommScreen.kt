package dev.turker.doge.ui.comm

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.turker.doge.R
import dev.turker.doge.data.CommsRepository
import dev.turker.doge.data.PostRepository
import dev.turker.doge.model.CommsCreate
import dev.turker.doge.ui.DogeNavigationActions
import dev.turker.doge.ui.DogeRoutes
import dev.turker.doge.ui.component.DogeButton
import dev.turker.doge.ui.component.DogeTextField
import kotlinx.coroutines.runBlocking

@Composable
fun CommScreen(navActions: DogeNavigationActions, postId: String) {
    val ctx = LocalContext.current
    var post = runBlocking {
        PostRepository().getPost(postId)
    }
    var text by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Köpeğin Sahibi İle İletişime Geç",
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            ),
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color.Gray)
        )

        DogeTextField(
            value = text,
            onValueChange = { text = it },
            placeholder = stringResource(R.string.comm_note),
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color.Gray)
        )

        DogeButton(
            onClick = {
                if (text.isNotBlank()) {
                    runBlocking {
                        CommsRepository().createComms(
                            CommsCreate(
                                message = text,
                                post = post.id,
                                receiver = post.userId
                            )
                        )
                        navActions.navigateTo(DogeRoutes.POSTS_ROUTE)
                        Toast.makeText(ctx, "Mesaj gönderildi", Toast.LENGTH_LONG).show()
                    }
                } else {
                    Toast.makeText(ctx, "Lütfen tüm alanları doldurun", Toast.LENGTH_LONG).show()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(id = R.string.comm_send))
        }
    }
}
