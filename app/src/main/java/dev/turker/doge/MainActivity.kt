package dev.turker.doge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dev.turker.doge.ui.DogeApp
import dev.turker.doge.ui.theme.DogeTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            DogeTheme {
                DogeApp()
            }
        }
    }
}
