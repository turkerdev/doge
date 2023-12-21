package dev.turker.doge.data

import dev.turker.doge.model.User
import dev.turker.doge.supabaseClient
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository {
    suspend fun getUser(id: String): User {
        return withContext(Dispatchers.IO) {
            supabaseClient.postgrest["hesaplar"]
                .select {
                    eq("id", id)
                }
                .decodeSingle()
        }
    }
}