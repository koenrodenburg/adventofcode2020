package day12.part1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Day12Part1 {
    private static final String INPUT_PATH = "src/main/resources/day12/input.txt";

    public static void main(String[] args) throws IOException {
        int orientation = 90;
        int posNorthSouth = 0;
        int postEastWest = 0;

        for (String instruction : Files.readAllLines(Path.of(INPUT_PATH))) {
            char action = instruction.charAt(0);
            int number = Integer.parseInt(instruction.substring(1));
            switch (action) {
                case 'N' -> posNorthSouth += number;
                case 'S' -> posNorthSouth -= number;
                case 'E' -> postEastWest += number;
                case 'W' -> postEastWest -= number;
                case 'L' -> {
                    orientation -= number;
                    if(orientation < 0) {
                        orientation += 360;
                    }
                }
                case 'R' -> {
                    orientation += number;
                    if(orientation >= 360) {
                        orientation -= 360;
                    }
                }
                case 'F' -> {
                    switch (orientation) {
                        case 0 -> posNorthSouth += number;
                        case 90 -> postEastWest += number;
                        case 180 -> posNorthSouth -= number;
                        case 270 -> postEastWest -= number;
                    }
                }
            }
        }

        int manhattanDistance = Math.abs(posNorthSouth) + Math.abs(postEastWest);

        System.out.println(manhattanDistance);
    }
}
