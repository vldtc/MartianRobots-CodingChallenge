package com.example.martianrobots_codingchallenge.data

data class Position(
    val x: Int,
    val y: Int
)

enum class Orientation {
    N,
    S,
    E,
    W
}

data class Robot(
    val initialPosition: Position,
    val initialOrientation: Orientation,
    var position: Position,
    var orientation: Orientation,
    var isLost: Boolean
)

val scents = mutableSetOf<Position>()

