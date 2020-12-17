package day17.part1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class Day17Part1 {
    private static final String INPUT_PATH = "src/main/resources/day17/input.txt";

    private static Map<Integer, List<List<Boolean>>> pocketDimension = new HashMap<>();

    public static void main(String[] args) throws IOException {
        parseInput();

        for (int i = 0; i < 6; i++) {
            cycle();
        }

        long noOfActiveCells = pocketDimension.values().stream()
                .flatMap(Collection::stream)
                .flatMap(Collection::stream)
                .filter(cell -> cell)
                .count();

        System.out.println(noOfActiveCells);
    }

    private static void cycle() {
        Map<Integer, List<List<Boolean>>> newState = new HashMap<>();

        int startZ = pocketDimension.keySet().stream().mapToInt(i -> i).min().orElse(0) - 1;
        int endZ = pocketDimension.keySet().stream().mapToInt(i -> i).max().orElse(0) + 1;

        int layersYSize = pocketDimension.get(0).size();
        int layersXSize = pocketDimension.get(0).get(0).size();

        for (int z = startZ; z <= endZ; z++) {
            List<List<Boolean>> newLayer = new ArrayList<>();
            for (int y = -1; y <= layersYSize; y++) {
                List<Boolean> newRow = new ArrayList<>();
                for (int x = -1; x <= layersXSize; x++) {
                    List<Boolean> neighbors = getNeighbors(z, y, x, layersYSize, layersXSize);
                    int noOfActiveNeighbors = (int) neighbors.stream().filter(neighbor -> neighbor).count();
                    boolean currentXYZValue =
                            !pocketDimension.containsKey(z) || y == -1 || y >= layersYSize || x == -1 || x >= layersXSize ?
                                    false :
                                    pocketDimension.get(z).get(y).get(x);
                    if (currentXYZValue) {
                        newRow.add(noOfActiveNeighbors == 2 || noOfActiveNeighbors == 3);
                    } else {
                        newRow.add(noOfActiveNeighbors == 3);
                    }
                }
                newLayer.add(newRow);
            }
            newState.put(z, newLayer);
        }

        pocketDimension = newState;
    }

    private static List<Boolean> getNeighbors(int centerZ, int centerY, int centerX, int layersYSize, int layersXSize) {
        ArrayList<Boolean> neighbors = new ArrayList<>();
        for (int z = centerZ - 1; z <= centerZ + 1; z++) {
            for (int y = centerY - 1; y <= centerY + 1; y++) {
                for (int x = centerX - 1; x <= centerX + 1; x++) {
                    if (z != centerZ || y != centerY || x != centerX) {
                        if (!pocketDimension.containsKey(z)
                                || y < 0 || y >= layersYSize
                                || x < 0 || x >= layersXSize) {
                            neighbors.add(false);
                        } else {
                            neighbors.add(pocketDimension.get(z).get(y).get(x));
                        }
                    }
                }
            }
        }
        return neighbors;
    }

    private static void parseInput() throws IOException {
        List<List<Boolean>> layer0 = new ArrayList<>();
        for (String line : Files.readAllLines(Path.of(INPUT_PATH))) {
            layer0.add(line.chars()
                    .mapToObj(i -> (char) i)
                    .map(c -> c.equals('#'))
                    .collect(Collectors.toList()));
        }
        pocketDimension.put(0, layer0);
    }
}
