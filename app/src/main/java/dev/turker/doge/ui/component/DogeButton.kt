package dev.turker.doge.ui.component

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.turker.doge.ui.theme.Primary

@Composable
fun DogeButton(
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier,
    backgroundColor: Color = Primary,
    content: @Composable RowScope.() -> Unit,
){
    Button(
        modifier = modifier
            .padding(
                start = 16.dp,
                end = 16.dp,
                top = 10.dp,
                bottom = 10.dp
            ),
        colors = ButtonDefaults.buttonColors(contentColor = Color.White, containerColor = backgroundColor),
        onClick = onClick,
        content = content)
}

@Preview
@Composable
fun PreviewDogeButton() {
    DogeButton{
        Text("Click me!")
    }
}
