package edu.farmingdale.pizzapartybottomnavbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GpaAppScreen() {
    var grade1 by remember { mutableStateOf("") }
    var grade2 by remember { mutableStateOf("") }
    var grade3 by remember { mutableStateOf("") }
    var gpa by remember { mutableStateOf("") }
    var backColor by remember { mutableStateOf(Color.Cyan) }
    var btnLabel by remember { mutableStateOf("Calculate GPA") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backColor)
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = grade1,
            onValueChange = { grade1 = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            label = { Text("Course 1 Grade") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color.Black.copy(alpha = 0.5f),
                unfocusedLabelColor = Color.Black.copy(alpha = 0.5f)
            ),
            singleLine = true
        )

        OutlinedTextField(
            value = grade2,
            onValueChange = { grade2 = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            label = { Text("Course 2 Grade") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color.Black.copy(alpha = 0.5f),
                unfocusedLabelColor = Color.Black.copy(alpha = 0.5f)
            ),
            singleLine = true
        )

        OutlinedTextField(
            value = grade3,
            onValueChange = { grade3 = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            label = { Text("Course 3 Grade") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color.Black.copy(alpha = 0.5f),
                unfocusedLabelColor = Color.Black.copy(alpha = 0.5f)
            ),
            singleLine = true
        )

        Button(
            onClick = {
                if (btnLabel == "Calculate GPA") {
                    val gpaVal = calGPA(grade1, grade2, grade3)
                    if (gpaVal != null) {
                        gpa = gpaVal.toString()
                        backColor = when {
                            gpaVal < 60 -> Color.Red
                            gpaVal in 60.0..79.0 -> Color.Yellow
                            else -> Color.Green
                        }
                        btnLabel = "Clear"
                    } else {
                        gpa = "Invalid input"
                    }
                } else {
                    grade1 = ""
                    grade2 = ""
                    grade3 = ""
                    gpa = ""
                    backColor = Color.Cyan
                    btnLabel = "Calculate GPA"
                }
            },
            modifier = Modifier
                .padding(top = 24.dp)
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF673AB7) // Purple color matching the image
            )
        ) {
            Text(btnLabel)
        }

        if (gpa.isNotEmpty()) {
            Text(
                text = "GPA: $gpa",
                modifier = Modifier.padding(top = 16.dp),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

fun calGPA(grade1: String, grade2: String, grade3: String): Double? {
    return try {
        val grades = listOf(grade1.toDouble(), grade2.toDouble(), grade3.toDouble())
        grades.average()
    } catch (e: NumberFormatException) {
        null
    }
}