package day3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Day3 {
    private static final String INPUT_PATH = "src/main/resources/day3/input.txt";
    private static final char TREE = '#';

    private static boolean[][] map;
    private static int mapWidth = 0;

    public static void main(String[] args) throws IOException {
        readInput();

        part1();

        part2();
    }

    private static void part1() {
        int trees = countTrees(1, 3);

        System.out.println("Part 1: " + trees);
    }

    private static void part2() {
        long trees1 = countTrees(1, 1);
        long trees2 = countTrees(1, 3);
        long trees3 = countTrees(1, 5);
        long trees4 = countTrees(1, 7);
        long trees5 = countTrees(2, 1);

        long result = trees1 * trees2 * trees3 * trees4 * trees5;

        System.out.println("Part 2: " + result);
    }

    private static int countTrees(int down, int right) {
        int numberOfTrees = 0;
        int currentX = 0;
        int currentY = 0;

        while(currentY < map.length) {
            if(map[currentY][currentX]) {
                numberOfTrees++;
            }

            // Update position
            currentY += down;
            currentX = (currentX + right) % mapWidth;
        }

        return numberOfTrees;
    }

    /**
     * true = tree, false = empty
     */
    private static void readInput() throws IOException {
        List<String> inputs = Files.readAllLines(Path.of(INPUT_PATH));

        mapWidth = inputs.get(0).length();
        map = new boolean[inputs.size()][mapWidth];

        for (int i = 0; i < inputs.size(); i++) {
            String string = inputs.get(i);
            for (int j = 0; j < string.length(); j++) {
                map[i][j] = string.charAt(j) == TREE;
            }
        }
    }
}
