# MartianRobots-CodingChallenge 🤖

This project has been developed to resolve the Martian Robots coding challenge. Here, you can find information about solution details and how to run the program.

**Martian Robots Coding Challenged designed in Kotlin as an Android Application**.

# Table of contents
1. [General information](#general-information)
   1. [Input](#input)
   2. [Output](#output)
2. [Screenshots] (#screenshots)
3. [Technologies](#technologies)
4. [Testing](#testing)


## General information ℹ️

MartianRobots app comes in handy when you want to control your robots on Mars, using a simple yet efficient Android Application.

### Input

You must provide:
* Grid upper-right coordinates.
* Robot initial position and orientation.
* Robot instructions (a robot can turn left (L), turn right (R) or move forward (F)).

An example would be:
```
5 3 - Grid Coordinates
1 1 E - Robot 1 Initial Coordinates and Orientation
RFRFRFRF - Robot 1 Instructions
3 2 N - Robot 2 Initial Coordinates and Orientation
FRRFLLFFRRFLL - Robot 2 Instructions
```

This input represents:
- A planet of size 6x4, whose:
  - lower-left coordinates are `(0,0)`
  - upper-right coordinates are `(5,3)`
- Robot 1 which is located in `(1,1)`, looking **EAST**.
- Its instructions are `RFRFRFRF`.
- Robot 2 which is locatied in `(3,2)`, looking **NORTH**.

### Output

After the robot has executed all the instructions, the system will save the final position of the robot and display it in a saved memory.

For each robot, there will be information about:
- Robot final coordinates and orientation.
- Information about lost robots.

For example, the output corresponding to the previous input would be:
```
1 1 E 
3 3 N LOST!

```
## Screenshots

![Screenshot 2023-06-19 at 09 53 19](https://github.com/vldtc/MartianRobots-CodingChallenge/assets/129045490/613c50de-3ad9-44f7-92b1-df8a47930770)
![Screenshot 2023-06-19 at 09 55 24](https://github.com/vldtc/MartianRobots-CodingChallenge/assets/129045490/1788e572-a5d0-4617-8a90-e96a05635b45)
![Screenshot 2023-06-19 at 09 55 48](https://github.com/vldtc/MartianRobots-CodingChallenge/assets/129045490/d12b0538-7041-49d9-92d3-49d6195e874d)
![Screenshot 2023-06-19 at 09 55 59](https://github.com/vldtc/MartianRobots-CodingChallenge/assets/129045490/7deca601-e0ce-47c6-9227-f2278d3e59cb)
![Screenshot 2023-06-19 at 09 57 13](https://github.com/vldtc/MartianRobots-CodingChallenge/assets/129045490/b853e06f-a3f5-4906-8412-5247bb07d229)

## Technologies

- **Kotlin** is used as primary programming language.
- **Jetpack Compose** is the toolkit used for building the UI.

## Testing 🐛

**JUnit4** and **Truth** libraries were used for testing.

Test cases
- turnLeft should change orientation to the left correctly.
- turnRight should change orientation to the right correctly.
- moveForward should update robot's position correctly.
- moveForward should mark robot as lost if it goes beyond the grid with no scent.
- moveForward should not mark robot as lost if it goes beyond the grid but there is a scent.
- isPositionWithinGrid should return true for positions within the grid.
- isPositionWithinGrid should return false for positions outside the grid.
- isPositionWithinGrid should return false when grid is null.
- parseGridSize should return the correct position for valid input.
- parseGridSize should return null for invalid input.
- parsePosition should return the correct position for valid input.
- parsePosition should return null for invalid input.
- parseOrientation should return the correct orientation for valid input.
- parseOrientation should return null for invalid input.
- processInstructions should update robot's position and orientation correctly when grid is 1, 1, position 0, 0 and orientation N.
- processInstructions should update robot's position and orientation correctly when grid is 1, 1, position 0, 0 and orientation N and moves out of grid.
- getRobotColor should return the correct color based on orientation.
