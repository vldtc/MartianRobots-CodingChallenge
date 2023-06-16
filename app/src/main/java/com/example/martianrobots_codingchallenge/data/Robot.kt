package com.example.martianrobots_codingchallenge.data

data class Position(val x: Int, val y: Int)

enum class Orientation{
    N, S, E, W
}

data class Robot(var position: Position, var orientation: Orientation, var isLost: Boolean)

fun turnLeft(orientation: Orientation): Orientation {
    return when (orientation) {
        Orientation.N -> Orientation.W
        Orientation.S -> Orientation.E
        Orientation.E -> Orientation.N
        Orientation.W -> Orientation.S
    }
}

fun turnRight(orientation: Orientation): Orientation {
    return when (orientation) {
        Orientation.N -> Orientation.E
        Orientation.S -> Orientation.W
        Orientation.E -> Orientation.S
        Orientation.W -> Orientation.N
    }
}

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

fun isPositionWithinGrid(position: Position, grid: Position?): Boolean {
    return grid != null && position.x >= 0 && position.x <= grid.x && position.y >= 0 && position.y <= grid.y
}