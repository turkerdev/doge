package dev.turker.doge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dev.turker.doge.job.JobDTO
import dev.turker.doge.jobact.JobCommunicateScreen
import dev.turker.doge.jobact.JobDetailScreen
import dev.turker.doge.mainact.JobCreateScreen
import dev.turker.doge.mainact.JobScreen
import dev.turker.doge.ui.theme.DogeTheme
import io.github.jan.supabase.postgrest.postgrest

class JobActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val id = intent.extras?.getSerializable("jobId") as Int

        setContent {
            DogeTheme {
                val navController = rememberNavController()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    NavHost(navController = navController, startDestination = "/"){
                        composable("/"){ JobDetailScreen(navController,id) }
                        composable("/iletisim"){ JobCommunicateScreen(navController,id) }
                    }

                }
            }
        }
    }
}
