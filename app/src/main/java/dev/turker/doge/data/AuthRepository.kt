package dev.turker.doge.data

import android.graphics.Bitmap
import dev.turker.doge.model.AuthCreate
import dev.turker.doge.supabaseClient
import io.github.jan.supabase.gotrue.gotrue
import io.github.jan.supabase.gotrue.providers.builtin.Email
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.storage.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream

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

    suspend fun register(
        emailz: String,
        passwordz: String,
        name: String,
        surname: String,
        photo: Bitmap,
        phone: String
    ) {
        val user = supabaseClient.gotrue.signUpWith(Email) {
            email = emailz
            password = passwordz
        }
        val imagePath = AuthRepository().uploadImage(photo)
        AuthRepository().insertUser(name, surname, imagePath, phone)
    }

    suspend fun insertUser(name: String, surname: String, photo: String, phone: String) {
        val hesap = AuthCreate(name, surname, photo, phone)
        supabaseClient.postgrest["hesaplar"].insert(hesap)
    }

    suspend fun uploadImage(image: Bitmap): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        val filename =
            "${supabaseClient.gotrue.currentUserOrNull()?.id}/${System.currentTimeMillis()}.png"

        return supabaseClient.storage["photos"].upload(filename, byteArray)
    }
}