import androidx.compose.ui.graphics.Color
import com.example.martianrobots_codingchallenge.data.Orientation
import com.example.martianrobots_codingchallenge.data.Position
import com.example.martianrobots_codingchallenge.data.Robot
import com.example.martianrobots_codingchallenge.domain.getRobotColor
import com.example.martianrobots_codingchallenge.domain.isPositionWithinGrid
import com.example.martianrobots_codingchallenge.domain.moveForward
import com.example.martianrobots_codingchallenge.domain.parseGridSize
import com.example.martianrobots_codingchallenge.domain.parseOrientation
import com.example.martianrobots_codingchallenge.domain.parsePosition
import com.example.martianrobots_codingchallenge.domain.processInstructions
import com.example.martianrobots_codingchallenge.domain.turnLeft
import com.example.martianrobots_codingchallenge.domain.turnRight
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class RobotFunctionsTest {

    @Test
    fun `turnLeft should change orientation to the left correctly`() {
        assertThat(turnLeft(Orientation.N)).isEqualTo(Orientation.W)
        assertThat(turnLeft(Orientation.S)).isEqualTo(Orientation.E)
        assertThat(turnLeft(Orientation.E)).isEqualTo(Orientation.N)
        assertThat(turnLeft(Orientation.W)).isEqualTo(Orientation.S)
    }

    @Test
    fun `turnRight should change orientation to the right correctly`() {
        assertThat(turnRight(Orientation.N)).isEqualTo(Orientation.E)
        assertThat(turnRight(Orientation.S)).isEqualTo(Orientation.W)
        assertThat(turnRight(Orientation.E)).isEqualTo(Orientation.S)
        assertThat(turnRight(Orientation.W)).isEqualTo(Orientation.N)
    }

    @Test
    fun `moveForward should update robot's position correctly`() {
        val grid = Position(5, 5)
        val scents = mutableSetOf<Position>()

        val robot1 = Robot(Position(0, 0), Orientation.N, Position(0, 0), Orientation.N, false)
        moveForward(robot1, grid, scents)
        assertThat(robot1.position).isEqualTo(Position(0, 1))

        val robot2 = Robot(Position(2, 3), Orientation.S, Position(2, 3), Orientation.S, false)
        moveForward(robot2, grid, scents)
        assertThat(robot2.position).isEqualTo(Position(2, 2))

        val robot3 = Robot(Position(4, 4), Orientation.E, Position(4, 4), Orientation.E, false)
        moveForward(robot3, grid, scents)
        assertThat(robot3.position).isEqualTo(Position(5, 4))

        val robot4 = Robot(Position(1, 1), Orientation.W, Position(1, 1), Orientation.W, false)
        moveForward(robot4, grid, scents)
        assertThat(robot4.position).isEqualTo(Position(0, 1))
    }


    @Test
    fun `moveForward should mark robot as lost if it goes beyond the grid with no scent`() {
        val grid = Position(5, 5)
        val scents = mutableSetOf<Position>()

        val robot = Robot(Position(5, 5), Orientation.N, Position(5,5), Orientation.N, false)
        moveForward(robot, grid, scents)
        assertThat(robot.isLost).isTrue()
    }

    @Test
    fun `moveForward should not mark robot as lost if it goes beyond the grid but there is a scent`() {
        val grid = Position(5, 5)
        val scents = mutableSetOf(Position(5, 5))

        val robot = Robot(Position(5, 5), Orientation.E, Position(5, 5), Orientation.E, false)
        moveForward(robot, grid, scents)
        assertThat(robot.isLost).isFalse()
    }

    @Test
    fun `isPositionWithinGrid should return true for positions within the grid`() {
        val grid = Position(5, 5)
        assertThat(isPositionWithinGrid(Position(2, 3), grid)).isTrue()
        assertThat(isPositionWithinGrid(Position(0, 0), grid)).isTrue()
        assertThat(isPositionWithinGrid(Position(5, 5), grid)).isTrue()
    }

    @Test
    fun `isPositionWithinGrid should return false for positions outside the grid`() {
        val grid = Position(5, 5)
        assertThat(isPositionWithinGrid(Position(6, 4), grid)).isFalse()
        assertThat(isPositionWithinGrid(Position(0, -1), grid)).isFalse()
        assertThat(isPositionWithinGrid(Position(7, 7), grid)).isFalse()
    }

    @Test
    fun `isPositionWithinGrid should return false when grid is null`() {
        val grid = null
        assertThat(isPositionWithinGrid(Position(2, 3), grid)).isFalse()
        assertThat(isPositionWithinGrid(Position(0, 0), grid)).isFalse()
        assertThat(isPositionWithinGrid(Position(5, 5), grid)).isFalse()
    }

    @Test
    fun `parseGridSize should return the correct position for valid input`() {
        assertThat(parseGridSize("5 5")).isEqualTo(Position(5, 5))
        assertThat(parseGridSize("3 7")).isEqualTo(Position(3, 7))
    }

    @Test
    fun `parseGridSize should return null for invalid input`() {
        assertThat(parseGridSize("10")).isNull()
        assertThat(parseGridSize("5 -2")).isNull()
        assertThat(parseGridSize("3 a")).isNull()
    }

    @Test
    fun `parsePosition should return the correct position for valid input`() {
        assertThat(parsePosition("2", "4")).isEqualTo(Position(2, 4))
    }

    @Test
    fun `parsePosition should return null for invalid input`() {
        assertThat(parsePosition("2", "a")).isNull()
        assertThat(parsePosition("x", "4")).isNull()
        assertThat(parsePosition("", "4")).isNull()
    }

    @Test
    fun `parseOrientation should return the correct orientation for valid input`() {
        assertThat(parseOrientation("N")).isEqualTo(Orientation.N)
        assertThat(parseOrientation("S")).isEqualTo(Orientation.S)
        assertThat(parseOrientation("E")).isEqualTo(Orientation.E)
        assertThat(parseOrientation("W")).isEqualTo(Orientation.W)
    }

    @Test
    fun `parseOrientation should return null for invalid input`() {
        assertThat(parseOrientation("X")).isNull()
        assertThat(parseOrientation("north")).isNull()
        assertThat(parseOrientation("")).isNull()
    }

    @Test
    fun `processInstructions should update robot's position and orientation correctly when grid is 1, 1, position 0, 0 and orientation N`() {
        val grid = Position(1, 1)

        val robot = Robot(Position(0, 0), Orientation.N, Position(0, 0), Orientation.N, false)

        processInstructions(robot, "FRF", grid)
        assertThat(robot.position).isEqualTo(Position(1, 1))
        assertThat(robot.orientation).isEqualTo(Orientation.E)
        assertThat(robot.isLost).isFalse()
    }
    @Test
    fun `processInstructions should update robot's position and orientation correctly when grid is 1, 1, position 0, 0 and orientation N and moves out of grid`() {
        val grid = Position(1, 1)

        val robot = Robot(Position(0, 0), Orientation.N, Position(0, 0), Orientation.N, false)

        processInstructions(robot, "FF", grid)
        assertThat(robot.position).isEqualTo(Position(0, 1))
        assertThat(robot.orientation).isEqualTo(Orientation.N)
        assertThat(robot.isLost).isTrue()
    }

    @Test
    fun `getRobotColor should return the correct color based on orientation`() {
        assertThat(getRobotColor(Orientation.N)).isEqualTo(Color.Green)
        assertThat(getRobotColor(Orientation.S)).isEqualTo(Color.Magenta)
        assertThat(getRobotColor(Orientation.E)).isEqualTo(Color.Yellow)
        assertThat(getRobotColor(Orientation.W)).isEqualTo(Color.Red)
    }
}
