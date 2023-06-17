import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.martianrobots_codingchallenge.R
import com.example.martianrobots_codingchallenge.data.Robot
import com.example.martianrobots_codingchallenge.domain.getRobotColor
import com.example.martianrobots_codingchallenge.domain.parseGridSize
import com.example.martianrobots_codingchallenge.domain.parseOrientation
import com.example.martianrobots_codingchallenge.domain.parsePosition
import com.example.martianrobots_codingchallenge.domain.processInstructions

@Composable
fun MartianRobotsScreen() {
    var gridSize by remember { mutableStateOf("") }
    var robotX by remember { mutableStateOf("") }
    var robotY by remember { mutableStateOf("") }
    var robotOrientation by remember { mutableStateOf("") }
    var robotInstructions by remember { mutableStateOf("") }
    val robots = remember { mutableStateListOf<Robot>() }

    val onBtnSubmit: () -> Unit = {
        if (robotX.toInt() < 51 && robotY.toInt() < 51 && robotInstructions.length < 101) {
            val grid = parseGridSize(gridSize)
            val position = parsePosition(robotX, robotY)
            val orientation = parseOrientation(robotOrientation)
            robotInstructions.trim()

            if (grid != null && position != null && orientation != null && robotInstructions.isNotEmpty()) {
                val robot = Robot(position, orientation, position, orientation, false)
                processInstructions(robot, robotInstructions, grid)
                robots.add(robot)
            }
        }

        robotX = ""
        robotY = ""
        robotOrientation = ""
        robotInstructions = ""
    }


    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item {
            GridSizeInput(gridSize = gridSize) { gridSize = it }
            RobotInstructionsSection(
                robotX = robotX,
                robotY = robotY,
                robotOrientation = robotOrientation,
                robotInstructions = robotInstructions,
                onBtnSubmit = onBtnSubmit,
                onXChanged = { robotX = it },
                onYChanged = { robotY = it },
                onOrientationChanged = { robotOrientation = it },
                onInstructionsChanged = { robotInstructions = it }
            )
            LazyRow() {
                item {
                    GridSizeOutput(gridSize = gridSize, robot = robots.lastOrNull())
                }
            }
            if (robots.isNotEmpty()) RobotList(robots = robots)
        }
    }
}

@Composable
fun GridSizeInput(gridSize: String, onGridSizeChanged: (String) -> Unit) {
    val gridSizeNumbers = gridSize.trim().split(" ")
    val showError = gridSizeNumbers.any { (it.toIntOrNull() ?: 0) > 50 }

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = gridSize,
            onValueChange = onGridSizeChanged,
            label = { Text(stringResource(R.string.grid_size)) },
            placeholder = { Text(stringResource(R.string.grid_size_ex)) },
            modifier = Modifier.weight(1f),
            isError = gridSize.isBlank() || showError,
            trailingIcon = {
                if (gridSize.isBlank() || showError) Icon(
                    Icons.Default.Warning,
                    contentDescription = "Error"
                )
            },
            supportingText = {
                if (gridSize.isBlank()) Text(text = stringResource(R.string.grid_error))
                else if (showError) Text(text = stringResource(R.string.max_value_50))
            }
        )
    }
}


@Composable
fun RobotInstructionsSection(
    robotX: String,
    robotY: String,
    robotOrientation: String,
    robotInstructions: String,
    onBtnSubmit: () -> Unit,
    onXChanged: (String) -> Unit,
    onYChanged: (String) -> Unit,
    onOrientationChanged: (String) -> Unit,
    onInstructionsChanged: (String) -> Unit
) {
    val maxXValue = 50
    val maxYValue = 50
    val maxInstructionsLength = 100

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row {
            OutlinedTextField(
                value = robotX,
                onValueChange = onXChanged,
                label = { Text(stringResource(R.string.pos_x)) },
                placeholder = { Text(stringResource(R.string.pos_x_ex), fontSize = 12.sp) },
                modifier = Modifier.weight(1f),
                isError = robotX.toIntOrNull() !in 0..maxXValue,
                trailingIcon = {
                    if (robotX.toIntOrNull() != null && robotX.toInt() > maxXValue) Icon(
                        Icons.Default.Warning,
                        contentDescription = "Error"
                    )
                },
                supportingText = {
                    Text(text = stringResource(id = R.string.max_value_50))
                }
            )
            Spacer(modifier = Modifier.width(16.dp))
            OutlinedTextField(
                value = robotY,
                onValueChange = onYChanged,
                label = { Text(stringResource(R.string.pos_y)) },
                placeholder = { Text(stringResource(R.string.pos_y_ex), fontSize = 12.sp) },
                modifier = Modifier.weight(1f),
                isError = robotY.toIntOrNull() !in 0..maxYValue,
                trailingIcon = {
                    if (robotY.toIntOrNull() != null && robotY.toInt() > maxYValue) Icon(
                        Icons.Default.Warning,
                        contentDescription = "Error"
                    )
                },
                supportingText = {
                    Text(text = stringResource(id = R.string.max_value_50))
                }
            )
            Spacer(modifier = Modifier.width(16.dp))
            OutlinedTextField(
                value = robotOrientation,
                onValueChange = onOrientationChanged,
                label = { Text(stringResource(R.string.robot_orientation), fontSize = 12.sp) },
                placeholder = { Text(stringResource(R.string.robot_orientation_ex)) },
                modifier = Modifier.weight(1f)
            )
        }

        Row {
            OutlinedTextField(
                value = robotInstructions,
                onValueChange = onInstructionsChanged,
                label = { Text(stringResource(R.string.robot_instructions)) },
                placeholder = { Text(stringResource(R.string.robot_instructions_ex)) },
                modifier = Modifier.weight(1f),
                isError = robotInstructions.length > maxInstructionsLength,
                trailingIcon = {
                    if (robotInstructions.length > maxInstructionsLength) Icon(
                        Icons.Default.Warning,
                        contentDescription = "Error"
                    )
                }
            )
        }
        Button(
            onClick = onBtnSubmit
        ) {
            Text(stringResource(R.string.btn_submit))
        }
    }
}


@Composable
fun GridSizeOutput(gridSize: String, robot: Robot?) {
    val grid = gridSize.split(" ").mapNotNull { it.toIntOrNull() }
    if (grid.size == 2 && grid[0] < 51 && grid[1] < 51) {
        val rows = grid[1] + 1
        val columns = grid[0] + 1

        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            repeat(rows) { row ->
                Row {
                    repeat(columns) { column ->
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .padding(2.dp)
                                .background(
                                    color =
                                    if (robot != null && robot.position.x == column && robot.position.y == rows - row - 1) getRobotColor(
                                        robot.orientation
                                    )
                                    else if (robot != null && robot.initialPosition.x == column && robot.initialPosition.y == rows - row - 1) getRobotColor(
                                        robot.initialOrientation
                                    )
                                    else Color.LightGray
                                )
                        ) {
                            if (robot != null && robot.position.x == column && robot.position.y == rows - row - 1) {
                                Text(
                                    text = robot.orientation.toString(),
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            } else if (robot != null && robot.initialPosition.x == column && robot.initialPosition.y == rows - row - 1) {
                                Text(
                                    text = robot.initialOrientation.toString(),
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RobotList(robots: List<Robot>) {
    Column {
        Text(stringResource(R.string.robots))
        Spacer(modifier = Modifier.height(8.dp))
        for (robot in robots) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    "Initial: ${robot.initialPosition.x}, ${robot.initialPosition.y} ${robot.initialOrientation}",
                    modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
                )
                if (robot.isLost) {
                    Text(
                        "Output: ${robot.position.x}, ${robot.position.y} ${robot.orientation} LOST!",
                        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
                    )
                } else {
                    Text(
                        "Output: ${robot.position.x}, ${robot.position.y} ${robot.orientation}",
                        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

