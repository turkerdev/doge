package dev.turker.doge

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
            var passwordVisible by rememberSaveable { mutableStateOf(false) }
            val ctx = LocalContext.current

            LaunchedEffect(Unit){
                listenAuth(ctx)
            }

            DogeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(10.dp,Alignment.CenterVertically),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        TextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp, end = 16.dp, top = 10.dp, bottom = 10.dp)
                                .shadow(elevation = 3.dp, shape = RoundedCornerShape(8.dp)),
                            label = {
                                Text(text = "Email")
                            },
                            colors = TextFieldDefaults.textFieldColors(
                                focusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent,
                                errorIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                focusedLabelColor = Color.Gray,
                                containerColor = Color.White
                            ),
                            value = email,
                            onValueChange = {email=it}
                        )
                        TextField(
                            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp, end = 16.dp, top = 10.dp, bottom = 10.dp)
                                .shadow(elevation = 3.dp, shape = RoundedCornerShape(8.dp)),
                            label = {
                                Text(text = "Sifre")
                            },
                            colors = TextFieldDefaults.textFieldColors(
                                focusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent,
                                errorIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                focusedLabelColor = Color.Gray,
                                containerColor = Color.White
                            ),
                            trailingIcon = {
                                val image = if (passwordVisible)
                                    Icons.Filled.Check
                                else Icons.Filled.CheckCircle

                                val description = if (passwordVisible) "Sifre gizle" else "Sifre goster"

                                IconButton(onClick = {passwordVisible = !passwordVisible}){
                                    Icon(imageVector  = image, description)
                                }
                            },
                            value = password,
                            onValueChange = {password=it}
                        )
                        Row {
                            Button(
                                modifier = Modifier
                                    .padding(
                                        start = 16.dp,
                                        end = 16.dp,
                                        top = 10.dp,
                                        bottom = 10.dp
                                    )
                                    .shadow(elevation = 3.dp, shape = RoundedCornerShape(64.dp)),
                                colors = ButtonDefaults.buttonColors(contentColor = Color.Black, containerColor = Color.White),
                                onClick = { register(email,password,ctx) })
                            {
                                Text(text = "Kayit ol")
                            }
                            Button(
                                modifier = Modifier
                                    .padding(
                                        start = 16.dp,
                                        end = 16.dp,
                                        top = 10.dp,
                                        bottom = 10.dp
                                    )
                                    .shadow(elevation = 3.dp, shape = RoundedCornerShape(64.dp)),
                                colors = ButtonDefaults.buttonColors(contentColor = Color.Black, containerColor = Color.White),
                                onClick = { login(email,password,ctx) })
                            {
                                Text(text = "Giris yap")
                            }
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

    private fun register(mail:String, pass:String,ctx:Context){
        lifecycleScope.launch {
            try {
                supabaseClient.gotrue.signUpWith(Email){
                    email=mail
                    password=pass
                }
            }
            catch (e:Exception){
                Toast.makeText(ctx,"Kayit olurken hata olustu: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun login(mail:String, pass:String,ctx:Context){
        lifecycleScope.launch {
            try
            {
                supabaseClient.gotrue.loginWith(Email){
                    email=mail
                    password=pass
                }
            }
            catch (e:Exception){
                Toast.makeText(ctx,"Giris yaparken hata olustu: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }
}
