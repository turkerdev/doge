package dev.turker.doge.ui.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.turker.doge.R
import dev.turker.doge.data.AuthRepository
import dev.turker.doge.ui.component.DogeButton
import dev.turker.doge.ui.component.DogeTextField
import kotlinx.coroutines.runBlocking

@Composable
fun AuthScreen() {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
        ) {

            Image(
                painter = painterResource(id = R.drawable.logodog),
                contentDescription = null,
                modifier = Modifier
                    .size(250.dp)
                    .align(Alignment.Center)
            )
        }
        DogeTextField(
            value = email,
            onValueChange = { email = it },
            placeholder = stringResource(R.string.email),
        )
        DogeTextField(
            value = password,
            onValueChange = { password = it },
            placeholder = stringResource(R.string.password),
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        ) {
            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                Icon(
                    painter = if (passwordVisible) painterResource(R.drawable.eye_slash)
                    else painterResource(R.drawable.eye),
                    "",
                    modifier = Modifier.size(16.dp),
                )
            }
        }
        Row {
            DogeButton(
                onClick = {
                    runBlocking {
                        AuthRepository().login(email, password)
                    }
                },
                modifier = Modifier
                    .width(500.dp)
            ) {
                Text(stringResource(R.string.login))
            }
        }

        Row {
            Text(
                text = "Hesabın yok mu? ",
                fontSize = 18.sp,
                modifier = Modifier.padding(start = 16.dp)
            )
            Text(
                text = "Kayıt ol",
                fontSize = 18.sp,
                modifier = Modifier
                    .clickable {
                        runBlocking {
                            AuthRepository().register(email, password)
                        }
                    }
                    .padding(start = 4.dp),
                color = Color.Blue,
                textDecoration = TextDecoration.Underline
            )
        }
    }
}