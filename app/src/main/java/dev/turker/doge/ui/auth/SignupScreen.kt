package dev.turker.doge.ui.auth

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import dev.turker.doge.R
import dev.turker.doge.data.AuthRepository
import dev.turker.doge.ui.component.DogeButton
import dev.turker.doge.ui.component.DogeTextField
import kotlinx.coroutines.runBlocking
import java.io.InputStream

@Composable
fun SignupScreen() {
    val ctx = LocalContext.current
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var surname by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        val inputStream: InputStream? = uri?.let { ctx.contentResolver.openInputStream(it) }
        inputStream?.use { stream ->
            val selectedBitmap = BitmapFactory.decodeStream(stream)
            bitmap = selectedBitmap
        }
    }


    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.Start
    )

    {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
        ) {

            Image(
                painter = painterResource(id = R.drawable.logodog2),
                contentDescription = null,
                modifier = Modifier
                    .size(250.dp)
                    .align(Alignment.Center)
            )
        }


        DogeButton(onClick = { launcher.launch("image/*") }) {
            Text("Resim seç")

        }
        Row(
            modifier = Modifier
                .fillMaxWidth()

        ) {
            DogeTextField(
                value = name,
                onValueChange = { name = it },
                placeholder = "İsim",
                modifier = Modifier
                    .weight(2f)

            )
            DogeTextField(
                value = surname,
                onValueChange = { surname = it },
                placeholder = "Soyisim",
                modifier = Modifier
                    .weight(2f)

            )
        }
        DogeTextField(
            value = phone,
            onValueChange = { phone = it },
            placeholder = "Telefon Numarası",
        )

        DogeTextField(
            value = email,
            onValueChange = { email = it },
            placeholder = "E-Posta",
        )
        DogeTextField(
            value = password,
            onValueChange = { password = it },
            placeholder = "Şifre",
        )




        DogeButton(
            onClick = {
                runBlocking {
                    if (name.isEmpty()) {
                        Toast.makeText(ctx, "İsim boş olamaz", Toast.LENGTH_SHORT).show()
                        return@runBlocking
                    }

                    if (surname.isEmpty()) {
                        Toast.makeText(ctx, "Soyisim boş olamaz", Toast.LENGTH_SHORT).show()
                        return@runBlocking
                    }

                    if (phone.isEmpty()) {
                        Toast.makeText(ctx, "Telefon numarası boş olamaz", Toast.LENGTH_SHORT)
                            .show()
                        return@runBlocking
                    }

                    if (email.isEmpty()) {
                        Toast.makeText(ctx, "E-Posta boş olamaz", Toast.LENGTH_SHORT).show()
                        return@runBlocking
                    }

                    if (password.isEmpty()) {
                        Toast.makeText(ctx, "Şifre boş olamaz", Toast.LENGTH_SHORT).show()
                        return@runBlocking
                    }

                    if (password.length < 6) {
                        Toast.makeText(ctx, "Şifre en az 6 karakter olmalıdır", Toast.LENGTH_SHORT)
                            .show()
                        return@runBlocking
                    }

                    if (bitmap == null) {
                        Toast.makeText(ctx, "Resim boş olamaz", Toast.LENGTH_SHORT).show()
                        return@runBlocking
                    }

                    try {
                        AuthRepository().register(email, password, name, surname, bitmap!!, phone)
                    } catch (e: Exception) {
                        Toast.makeText(ctx, "Hatalı Kayıt: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            },
            modifier = Modifier
                .width(500.dp)
        ) {
            Text("Kayit Ol")
        }
    }
}