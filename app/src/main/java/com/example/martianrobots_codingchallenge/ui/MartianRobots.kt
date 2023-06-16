package com.example.martianrobots_codingchallenge.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun MartianRobotsScreen() {


    Column {
        ChallengeHeader()
        GridSizeSection()
        RobotInitialPositionSection()
        RobotInstructionsSection()
        RobotOutputsSection()
    }
}

@Composable
fun ChallengeHeader() {
    Text(
        text = "Martian Robots",
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.headlineLarge,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    )
}

@Composable
fun GridSizeSection() {
    var grid by remember { mutableStateOf("") }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = grid,
            onValueChange = { grid = it },
            label = {
                Text(
                    text = "Grid size"
                )
            },
            placeholder = {
                Text(
                    text = "Example: 5 3"
                )
            },
            modifier = Modifier
                .padding(vertical = 8.dp)
                .weight(2f)
        )
        Button(
            onClick = {
                // Handle button click event
            },
            modifier = Modifier
                .padding(8.dp)
                .weight(1f)
        ) {
            Text(text = "Submit!")
        }
    }
}

@Composable
fun RobotInitialPositionSection() {
    var initialPosition by remember { mutableStateOf("") }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = initialPosition,
            onValueChange = { initialPosition = it },
            label = {
                Text(
                    text = "Initial robot position"
                )
            },
            placeholder = {
                Text(
                    text = "Example: 1 1 N"
                )
            },
            modifier = Modifier
                .padding(vertical = 8.dp)
                .weight(2f)
        )
        Button(
            onClick = {
                // Handle button click event
            },
            modifier = Modifier
                .padding(8.dp)
                .weight(1f)
        ) {
            Text(text = "Submit!")
        }
    }
}

@Composable
fun RobotInstructionsSection() {
    var robotInstructions by remember { mutableStateOf("") }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = robotInstructions,
            onValueChange = { robotInstructions = it },
            label = {
                Text(
                    text = "Robot instructions"
                )
            },
            placeholder = {
                Text(
                    text = "Example: RFRFRFRF"
                )
            },
            modifier = Modifier
                .padding(vertical = 8.dp)
                .weight(2f)
        )
        Button(
            onClick = {
                // Handle button click event
            },
            modifier = Modifier
                .padding(8.dp)
                .weight(1f)
        ) {
            Text(text = "Submit!")
        }
    }
}

@Composable
fun RobotOutputsSection() {
    var finalPosition by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = finalPosition,
            onValueChange = { finalPosition = it },
            label = {
                Text(
                    text = "Final position and orientation of the robot"
                )
            },
            readOnly = true,
            modifier = Modifier.padding(vertical = 8.dp)
        )
    }
}
