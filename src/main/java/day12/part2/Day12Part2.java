package day12.part2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Day12Part2 {
    private static final String INPUT_PATH = "src/main/resources/day12/input.txt";

    public static void main(String[] args) throws IOException {
        int waypointRelativePosNorthSouth = 1;
        int waypointRelativePosEastWest = 10;
        int shipPosNorthSouth = 0;
        int shipPosEastWest = 0;

        for (String instruction : Files.readAllLines(Path.of(INPUT_PATH))) {
            char action = instruction.charAt(0);
            int number = Integer.parseInt(instruction.substring(1));
            switch (action) {
                case 'N' -> waypointRelativePosNorthSouth += number;
                case 'S' -> waypointRelativePosNorthSouth -= number;
                case 'E' -> waypointRelativePosEastWest += number;
                case 'W' -> waypointRelativePosEastWest -= number;
                case 'F' -> {
                    shipPosEastWest += number * waypointRelativePosEastWest;
                    shipPosNorthSouth += number * waypointRelativePosNorthSouth;
                }
                case 'L' -> {
                    if (number % 360 == 90) {
                        int tmpNorth = waypointRelativePosNorthSouth;
                        waypointRelativePosNorthSouth = waypointRelativePosEastWest;
                        waypointRelativePosEastWest = -1 * tmpNorth;
                    } else if (number % 360 == 180) {
                        waypointRelativePosNorthSouth *= -1;
                        waypointRelativePosEastWest *= -1;
                    } else if (number % 360 == 270) {
                        int tmpNorth = waypointRelativePosNorthSouth;
                        waypointRelativePosNorthSouth = -1 * waypointRelativePosEastWest;
                        waypointRelativePosEastWest = tmpNorth;
                    }
                }
                case 'R' -> {
                    if (number % 360 == 90) {
                        int tmpNorth = waypointRelativePosNorthSouth;
                        waypointRelativePosNorthSouth = -1 * waypointRelativePosEastWest;
                        waypointRelativePosEastWest = tmpNorth;
                    } else if (number % 360 == 180) {
                        waypointRelativePosNorthSouth *= -1;
                        waypointRelativePosEastWest *= -1;
                    } else if (number % 360 == 270) {
                        int tmpNorth = waypointRelativePosNorthSouth;
                        waypointRelativePosNorthSouth = waypointRelativePosEastWest;
                        waypointRelativePosEastWest = -1 * tmpNorth;
                    }
                }
            }
        }

        int manhattanDistance = Math.abs(shipPosNorthSouth) + Math.abs(shipPosEastWest);

        System.out.println(manhattanDistance);
    }
}
