package edu.farmingdale.pizzapartybottomnavbar

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Screen3() {
    var sliderValue by remember { mutableStateOf(0.5f) }
    var chkd by remember { mutableStateOf(true) }

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color.Cyan, Color.Blue)
                )
            )
            .padding(20.dp), // Add padding around the whole column
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Slider to change text value
        Slider(
            value = sliderValue,
            onValueChange = { sliderValue = it },
            modifier = Modifier.fillMaxWidth(),
            enabled = chkd
        )

        // Display slider value as text
        Text(
            fontSize = 20.sp,
            text = "Slider value: ${String.format("%.2f", sliderValue)}",
            modifier = Modifier.padding(vertical = 20.dp) // Add padding above/below the text
        )

        // Button to make a call
        Button(onClick = {
            val newInt = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("tel:6314202000")
            }
            context.startActivity(newInt)
        }) {
            Text(fontSize = 20.sp, text = "Call me")
        }

        // Checkbox to enable/disable slider
        Checkbox(checked = chkd, onCheckedChange = { chkd = it }, modifier = Modifier.padding(10.dp))
    }
}




