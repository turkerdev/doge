package dev.turker.doge.data

import dev.turker.doge.model.CommsCreate
import dev.turker.doge.supabaseClient
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CommsRepository {
    private val commsTable = "comms"

    suspend fun createComms(comms: CommsCreate) {
        return withContext(Dispatchers.IO) {
            supabaseClient.postgrest[commsTable]
                .insert(comms)
        }
    }
}