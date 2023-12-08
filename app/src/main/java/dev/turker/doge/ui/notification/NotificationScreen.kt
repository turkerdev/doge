package dev.turker.doge.ui.notification

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.turker.doge.data.CommsRepository
import dev.turker.doge.supabaseClient
import dev.turker.doge.ui.DogeNavigationActions
import io.github.jan.supabase.gotrue.gotrue
import kotlinx.coroutines.runBlocking

@Composable
fun NotificationScreen(navActions: DogeNavigationActions) {
    val listState = rememberLazyListState()
    val messages = runBlocking {
        CommsRepository().getComms(supabaseClient.gotrue.currentUserOrNull()?.id ?: "")
    }

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        state = listState,
        modifier = Modifier.padding(8.dp, 0.dp)
    ) {
        items(messages) {
            NotificationCard(comms = it, onPostClick = {})
        }
    }
}