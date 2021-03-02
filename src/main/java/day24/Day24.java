package day24;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Day24 {
    private static final String INPUT_PATH = "src/main/resources/day24/input.txt";

    private static boolean[][] FLOOR = new boolean[500][500];

    private static final int REFERENCE_X = FLOOR.length / 2;
    private static final int REFERENCE_Y = FLOOR[0].length / 2;

    public static void main(String[] args) throws IOException {
        for (String path : Files.readAllLines(Path.of(INPUT_PATH))) {
            flipTile(path);
        }

        System.out.println("Part 1: " + countBlackTiles());

        for (int i = 0; i < 100; i++) {
            FLOOR = flipFloor();
        }

        System.out.println("Part 2: " + countBlackTiles());
    }

    private static boolean[][] flipFloor() {
        boolean[][] newFloor = new boolean[FLOOR.length][FLOOR[0].length];

        for (int i = 1; i < FLOOR.length - 1; i++) {
            for (int j = i % 2 == 0 ? 2 : 3; j < FLOOR[i].length - 3; j += 2) {
                int numberOfBlackNeighbors = countBlackNeighbors(i, j);

                if (FLOOR[i][j]) {
                    newFloor[i][j] = numberOfBlackNeighbors != 0 && numberOfBlackNeighbors <= 2;
                } else {
                    newFloor[i][j] = numberOfBlackNeighbors == 2;
                }
            }
        }

        return newFloor;
    }

    private static int countBlackNeighbors(int y, int x) {
        int count = 0;

        if (FLOOR[y][x - 2]) {
            count++;
        }
        if (FLOOR[y][x + 2]) {
            count++;
        }
        if (FLOOR[y - 1][x - 1]) {
            count++;
        }
        if (FLOOR[y - 1][x + 1]) {
            count++;
        }
        if (FLOOR[y + 1][x - 1]) {
            count++;
        }
        if (FLOOR[y + 1][x + 1]) {
            count++;
        }

        return count;
    }

    private static void flipTile(String path) {
        int currentX = REFERENCE_X;
        int currentY = REFERENCE_Y;

        int i = 0;
        while (i < path.length()) {
            if ("e".equals(path.substring(i, i + 1))) {
                currentX -= 2;
                i++;
            } else if ("w".equals(path.substring(i, i + 1))) {
                currentX += 2;
                i++;
            } else if ("ne".equals(path.substring(i, i + 2))) {
                currentY--;
                currentX--;
                i += 2;
            } else if ("nw".equals(path.substring(i, i + 2))) {
                currentY--;
                currentX++;
                i += 2;
            } else if ("se".equals(path.substring(i, i + 2))) {
                currentY++;
                currentX--;
                i += 2;
            } else if ("sw".equals(path.substring(i, i + 2))) {
                currentY++;
                currentX++;
                i += 2;
            }
        }

        FLOOR[currentY][currentX] = !FLOOR[currentY][currentX];
    }

    private static int countBlackTiles() {
        int blackTiles = 0;

        for (boolean[] line : FLOOR) {
            for (boolean tile : line) {
                if (tile) {
                    blackTiles++;
                }
            }
        }

        return blackTiles;
    }
}