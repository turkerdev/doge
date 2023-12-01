package dev.turker.doge.data

import dev.turker.doge.model.Post
import dev.turker.doge.model.PostCreate
import dev.turker.doge.supabaseClient
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PostRepository {
    suspend fun getFeed(): List<Post> {
        return withContext(Dispatchers.IO) {
            supabaseClient.postgrest["ilanlar"]
                .select()
                .decodeList()
        }
    }

    suspend fun getPost(id: String): Post {
        return withContext(Dispatchers.IO) {
            supabaseClient.postgrest["ilanlar"]
                .select {
                    eq("id", id)
                }
                .decodeSingle()
        }
    }

    suspend fun createPost(post: PostCreate): Post {
        return withContext(Dispatchers.IO) {
            supabaseClient.postgrest["ilanlar"].insert(post).decodeSingle()
        }
    }
}