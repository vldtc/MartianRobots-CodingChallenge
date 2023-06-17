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
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
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
import com.example.martianrobots_codingchallenge.data.getRobotColor

@Composable
fun MartianRobotsScreen() {
    var gridSize by remember { mutableStateOf("") }
    val robotX by remember { mutableStateOf("") }
    val robotY by remember { mutableStateOf("") }
    val robotOrientation by remember { mutableStateOf("") }
    val robotInstructions by remember { mutableStateOf("") }
    val robots = remember { mutableStateListOf<Robot>() }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item{
            if(gridSize == ""){
                GridError()
            }
            GridSizeInput(gridSize = gridSize) { gridSize = it }
            RobotInstructionsSection(
                gridSize = gridSize,
                robotX = robotX,
                robotY = robotY,
                robotOrientation = robotOrientation,
                robotInstructions = robotInstructions
            )
            GridSizeOutput(gridSize = gridSize, robot = robots.lastOrNull())
            RobotList(robots = robots)
        }
    }
}

@Composable
fun GridError(){
    Text(
        text = stringResource(R.string.grid_error),
        modifier = Modifier
            .fillMaxWidth(),
        textAlign = TextAlign.Center,
        color = Color.Red
    )
}
@Composable
fun GridSizeInput(gridSize: String, onGridSizeChanged: (String) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = gridSize,
            onValueChange = onGridSizeChanged,
            label = { Text(stringResource(R.string.grid_size)) },
            placeholder = { Text(stringResource(R.string.grid_size_ex)) },
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun RobotInstructionsSection(
    gridSize: String,
    robotX: String,
    robotY: String,
    robotOrientation: String,
    robotInstructions: String,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row {
            OutlinedTextField(
                value = robotX,
                onValueChange = { },
                label = { Text(stringResource(R.string.pos_x)) },
                placeholder = { Text(stringResource(R.string.pos_x_ex)) },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(16.dp))
            OutlinedTextField(
                value = robotY,
                onValueChange = { },
                label = { Text(stringResource(R.string.pos_y)) },
                placeholder = { Text(stringResource(R.string.pos_y_ex)) },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(16.dp))
            OutlinedTextField(
                value = robotOrientation,
                onValueChange = { },
                label = { Text(stringResource(R.string.robot_orientation), fontSize = 12.sp) },
                placeholder = { Text(stringResource(R.string.robot_orientation_ex)) },
                modifier = Modifier.weight(1f)
            )
        }

        Row {
            OutlinedTextField(
                value = robotInstructions,
                onValueChange = { },
                label = { Text(stringResource(R.string.robot_instructions)) },
                placeholder = { Text(stringResource(R.string.robot_instructions_ex)) },
                modifier = Modifier.weight(1f)
            )
        }
        Button(
            onClick = { }
        ) {
            Text(stringResource(R.string.btn_submit))
        }
    }
}

@Composable
fun GridSizeOutput(gridSize: String, robot: Robot?) {
    val grid = gridSize.split(" ").mapNotNull { it.toIntOrNull() }
    if (grid.size == 2) {
        val rows = grid[1] + 1
        val columns = grid[0] + 1

        Column (
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
                ){
            Divider(modifier = Modifier.padding(vertical = 4.dp))
            repeat(rows) { row ->
                Row {
                    repeat(columns) { column ->
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .padding(2.dp)
                                .background(
                                    color = if (robot != null && robot.position.x == column && robot.position.y == rows - row - 1) {
                                        getRobotColor(robot.orientation)
                                    } else {
                                        Color.LightGray
                                    }
                                )
                        )
                    }
                }
            }
            Divider(modifier = Modifier.padding(vertical = 4.dp))
        }
    } else {
        // Handle incorrect grid size input
        // Display an error message or show a toast
    }
}

@Composable
fun RobotList(robots: List<Robot>) {
    Column {
        Text(stringResource(R.string.robots))
        Spacer(modifier = Modifier.height(8.dp))
        for (robot in robots) {
            Card() {
                Text("Initial Position: (${robot.initialPosition.x}, ${robot.initialPosition.y}), Orientation: ${robot.initialOrientation}")
                Text("Output Position: (${robot.position.x}, ${robot.position.y}), Orientation: ${robot.orientation}")
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

