package com.example.martianrobots_codingchallenge.domain

import androidx.compose.ui.graphics.Color
import com.example.martianrobots_codingchallenge.data.Orientation
import com.example.martianrobots_codingchallenge.data.Position
import com.example.martianrobots_codingchallenge.data.Robot
import com.example.martianrobots_codingchallenge.data.scents

//Change orientation to left
fun turnLeft(orientation: Orientation): Orientation {
    return when (orientation) {
        Orientation.N -> Orientation.W
        Orientation.S -> Orientation.E
        Orientation.E -> Orientation.N
        Orientation.W -> Orientation.S
    }
}

//Change orientation to right
fun turnRight(orientation: Orientation): Orientation {
    return when (orientation) {
        Orientation.N -> Orientation.E
        Orientation.S -> Orientation.W
        Orientation.E -> Orientation.S
        Orientation.W -> Orientation.N
    }
}

//Move forward one position
fun moveForward(robot: Robot, grid: Position?, scents: MutableSet<Position>) {
    val newPosition = when (robot.orientation) {
        Orientation.N -> Position(robot.position.x, robot.position.y + 1)
        Orientation.S -> Position(robot.position.x, robot.position.y - 1)
        Orientation.E -> Position(robot.position.x + 1, robot.position.y)
        Orientation.W -> Position(robot.position.x - 1, robot.position.y)
    }

    if (isPositionWithinGrid(newPosition, grid)) {
        robot.position = newPosition
    } else {
        if (!scents.contains(robot.position)) {
            robot.isLost = true
            scents.add(robot.position)
        }
    }
}

//Check if position is withing grid
fun isPositionWithinGrid(position: Position, grid: Position?): Boolean {
    return grid != null && position.x >= 0 && position.x <= grid.x && position.y >= 0 && position.y <= grid.y
}

//Parse grid size input
fun parseGridSize(input: String): Position? {
    val dimensions = input.trim().split(" ")
    if (dimensions.size != 2) {
        return null
    }

    val x = dimensions[0].toIntOrNull()
    val y = dimensions[1].toIntOrNull()

    if (x == null || y == null || x < 0 || y < 0) {
        return null
    }

    return Position(x, y)
}

//Parse position input
fun parsePosition(xInput: String, yInput: String): Position? {
    val x = xInput.trim().toIntOrNull()
    val y = yInput.trim().toIntOrNull()

    if (x == null || y == null) {
        return null
    }

    return Position(x, y)
}

//Parse orientation input
fun parseOrientation(input: String): Orientation? {
    return try {
        Orientation.valueOf(input.trim())
    } catch (e: IllegalArgumentException) {
        null
    }
}

//Function to processes the instructions accordingly
fun processInstructions(robot: Robot, instructions: String, grid: Position?) {
    instructions.forEach {
        if (robot.isLost) return@forEach

        when (it) {
            'L' -> robot.orientation = turnLeft(robot.orientation)
            'R' -> robot.orientation = turnRight(robot.orientation)
            'F' -> moveForward(robot, grid, scents)
        }
    }
}

//Get robot colour based on orientation
fun getRobotColor(orientation: Orientation): Color {
    return when (orientation) {
        Orientation.N -> Color.Green
        Orientation.S -> Color.Magenta
        Orientation.E -> Color.Yellow
        Orientation.W -> Color.Red
    }
}
