package dev.turker.doge.mainact

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import dev.turker.doge.job.JobDTO
import dev.turker.doge.job.JobContent
import dev.turker.doge.supabaseClient
import io.github.jan.supabase.gotrue.gotrue
import io.github.jan.supabase.postgrest.postgrest

@Composable
fun JobScreen(navController: NavController){
    var jobs = remember { mutableStateListOf<JobDTO>() }

    LaunchedEffect(Unit){
        supabaseClient.postgrest["ilanlar"]
            .select{
                eq("user_id", supabaseClient.gotrue.currentSessionOrNull()?.user!!.id)}
            .decodeList<JobDTO>()
            .forEach{jobs.add(it)};
    }

    Column {
        jobs.forEach{
            JobContent(job = it)
        }
    }
}