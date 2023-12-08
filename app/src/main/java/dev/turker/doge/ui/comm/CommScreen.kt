package dev.turker.doge.ui.comm

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
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
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        Text(stringResource(R.string.comm_note), Modifier.padding(8.dp))
        DogeTextField(
            value = text,
            onValueChange = { text = it }
        )
        DogeButton(
            onClick = {
                runBlocking {
                    CommsRepository().createComms(
                        CommsCreate(
                            message = text,
                            post = post.id,
                            receiver = post.userId
                        )
                    )
                }
                navActions.navigateTo(DogeRoutes.POSTS_ROUTE)
                Toast.makeText(ctx, "Message sent", Toast.LENGTH_LONG).show()
            })
        {
            Text(stringResource(R.string.comm_send))
        }
    }
}