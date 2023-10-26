package dev.turker.doge

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.lifecycleScope
import dev.turker.doge.ui.theme.DogeTheme
import io.github.jan.supabase.gotrue.SessionStatus
import io.github.jan.supabase.gotrue.gotrue
import io.github.jan.supabase.gotrue.providers.builtin.Email
import kotlinx.coroutines.launch
import java.util.logging.Logger

class AuthActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            var email by remember { mutableStateOf("") }
            var password by remember { mutableStateOf("") }
            val ctx = LocalContext.current

            LaunchedEffect(Unit){
                listenAuth(ctx)
            }

            DogeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    Column {
                        TextField(
                            value = email,
                            onValueChange = {email=it}
                        )
                        TextField(
                            value = password,
                            onValueChange = {password=it}
                        )
                        Button(onClick = { register(email,password) }) {
                            Text(text = "register")
                        }
                        Button(onClick = { login(email,password) }) {
                            Text(text = "login")
                        }
                    }
                }
            }
        }
    }

    private fun listenAuth(ctx:Context){
        lifecycleScope.launch {
            supabaseClient.gotrue.sessionStatus.collect {
                when(it) {
                    is SessionStatus.Authenticated -> {
                        val intent = Intent(ctx, MainActivity::class.java)
                        ctx.startActivity(intent)
                    }
                    SessionStatus.LoadingFromStorage -> Logger.getGlobal().info("xxLoading from storage")
                    SessionStatus.NetworkError -> Logger.getGlobal().info("xxNetwork error")
                    SessionStatus.NotAuthenticated -> Logger.getGlobal().info("xxNot authenticated")
                }
            }
        }
    }

    private fun register(mail:String, pass:String){
        lifecycleScope.launch {
            supabaseClient.gotrue.signUpWith(Email){
                email=mail
                password=pass
            }
        }
    }

    private fun login(mail:String, pass:String){
        lifecycleScope.launch {
            supabaseClient.gotrue.loginWith(Email){
                email=mail
                password=pass
            }
        }
    }
}
