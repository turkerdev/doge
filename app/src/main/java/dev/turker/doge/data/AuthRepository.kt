package dev.turker.doge.data

import dev.turker.doge.supabaseClient
import io.github.jan.supabase.gotrue.gotrue
import io.github.jan.supabase.gotrue.providers.builtin.Email
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthRepository {
    suspend fun login(emailz: String, passwordz: String) {
        return withContext(Dispatchers.IO) {
            try {
                supabaseClient.gotrue.loginWith(Email) {
                    email = emailz
                    password = passwordz
                }
                Result.success("")
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    suspend fun register(emailz: String, passwordz: String) {
        return withContext(Dispatchers.IO) {
            try {
                supabaseClient.gotrue.signUpWith(Email) {
                    email = emailz
                    password = passwordz
                }
                Result.success("")
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}