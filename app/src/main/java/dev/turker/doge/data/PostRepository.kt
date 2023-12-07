package dev.turker.doge.data

import android.graphics.Bitmap
import dev.turker.doge.model.Post
import dev.turker.doge.model.PostCreate
import dev.turker.doge.supabaseClient
import io.github.jan.supabase.gotrue.gotrue
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.storage.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream

class PostRepository {
    private val postTable = "ilanlar"

    suspend fun getFeed(): List<Post> {
        return withContext(Dispatchers.IO) {
            supabaseClient.postgrest[postTable]
                .select()
                .decodeList()
        }
    }

    suspend fun getPost(id: String): Post {
        return withContext(Dispatchers.IO) {
            supabaseClient.postgrest[postTable]
                .select {
                    eq("id", id)
                }
                .decodeSingle()
        }
    }

    suspend fun uploadImage(image: Bitmap): String {
        return withContext(Dispatchers.IO) {
            val byteArrayOutputStream = ByteArrayOutputStream()
            image.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
            val byteArray = byteArrayOutputStream.toByteArray()
            val filename =
                "${supabaseClient.gotrue.currentUserOrNull()?.id}/${System.currentTimeMillis()}.png"

            supabaseClient.storage["doges"].upload(filename, byteArray)
        }
    }

    suspend fun createPost(post: PostCreate): Post {
        return withContext(Dispatchers.IO) {
            supabaseClient.postgrest[postTable].insert(post).decodeSingle()
        }
    }
}